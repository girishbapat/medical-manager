package ar.com.lodev.medical_manager.component.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.ChatMedicalSessionMessageRepository;
import ar.com.lodev.medical_manager.component.dao.ChatMedicalSessionRepository;
import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.dao.PracticePlaceRepository;
import ar.com.lodev.medical_manager.component.service.ChatMedicalSessionService;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.NotificationService;
import ar.com.lodev.medical_manager.model.ChatMedicalSession;
import ar.com.lodev.medical_manager.model.ChatMedicalSessionMessage;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.Patient;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.ChatMedicalSessionDTO;

@Service
public class ChatMedicalSessionServiceImpl extends BaseService implements ChatMedicalSessionService {

	@Autowired
	private ChatMedicalSessionRepository chatMedicalSessionRepository;
	@Autowired
	private ChatMedicalSessionMessageRepository chatMessageRepository;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private MedicalSessionRepository sessionRepository;
	@Autowired
	private PracticePlaceRepository practicePlaceRepository;
	@Autowired
	private NotificationService notificationService;

	@Override
	@Transactional
	public void createChatForDoctor(Doctor doctor, MedicalSession session) {
		ChatMedicalSession chatSession = chatMedicalSessionRepository.findByDoctorIdAndSessionId(doctor.getId(),
				session.getId());
		if (chatSession == null) {
			chatSession = new ChatMedicalSession(doctor, session);
			chatMedicalSessionRepository.save(chatSession);
		}
	}

	@Override
	@Transactional
	public void createChatForPractice(MedicalSession session) {
		ChatMedicalSession chatSession = new ChatMedicalSession(session.getPracticePlace(), session);
		chatMedicalSessionRepository.save(chatSession);
	}

	@Override
	@Transactional(readOnly = true)
	public ChatMedicalSessionDTO findChatFromDoctor(long sessionId) {
		Doctor doctor = doctorService.getEntityFromSession();
		MedicalSession session = sessionRepository.findOne(sessionId);
		if (!session.getDoctor().equals(doctor)) {
			throw new SecurityException("you can't access to this chat");
		}
		ChatMedicalSession chatSession = chatMedicalSessionRepository.findByDoctorIdAndSessionId(doctor.getId(),
				session.getId());
		if (chatSession == null) {
			chatSession = new ChatMedicalSession(doctor, session);
			chatMedicalSessionRepository.save(chatSession);
		}
		return new ChatMedicalSessionDTO(chatSession);
	}

	@Override
	@Transactional
	public ChatMedicalSessionDTO findChatFromPractice(long sessionId) {
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		MedicalSession session = sessionRepository.findOne(sessionId);
		if (!session.getPracticePlace().equals(practicePlace)) {
			throw new SecurityException("you can't access to this chat");
		}
		ChatMedicalSession chatSession = chatMedicalSessionRepository
				.findByPracticePlaceIdAndSessionId(practicePlace.getId(), session.getId());
		if (chatSession == null) {
			chatSession = new ChatMedicalSession(practicePlace, session);
			chatSession = chatMedicalSessionRepository.save(chatSession);
		}
		return new ChatMedicalSessionDTO(chatSession);
	}

	@Override
	@Transactional
	public void createMessageFromDoctor(long chatSessionId, String message) {
		Doctor doctor = doctorService.getEntityFromSession();
		ChatMedicalSession chatSession = chatMedicalSessionRepository.findOne(chatSessionId);
		if (!chatSession.getDoctor().equals(doctor)) {
			throw new SecurityException("you can't access to this chat");
		}
		ChatMedicalSessionMessage chatMessage = new ChatMedicalSessionMessage(chatSession, message, true);
		chatMessageRepository.save(chatMessage);
		Patient patient = chatSession.getSession().getPatient();
		if (patient.getGcmId() != null && !patient.getGcmId().isEmpty()) {
			notificationService.notifyNewMessage(doctor, patient.getGcmId(), message, chatMessage.getDateCreated());
		}
	}

	@Override
	@Transactional
	public void createMessageFromPractice(long chatSessionId, String message) {
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		ChatMedicalSession chatSession = chatMedicalSessionRepository.findOne(chatSessionId);
		if (!chatSession.getPracticePlace().equals(practicePlace)) {
			throw new SecurityException("you can't access to this chat");
		}
		ChatMedicalSessionMessage chatMessage = new ChatMedicalSessionMessage(chatSession, message, true);
		chatMessageRepository.save(chatMessage);

		Patient patient = chatSession.getSession().getPatient();
		if (patient.getGcmId() != null && !patient.getGcmId().isEmpty()) {
			notificationService.notifyNewMessage(practicePlace, patient.getGcmId(), message,
					chatMessage.getDateCreated());
		}
	}
}
