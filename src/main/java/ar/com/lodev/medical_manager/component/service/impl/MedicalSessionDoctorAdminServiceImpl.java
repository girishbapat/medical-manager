package ar.com.lodev.medical_manager.component.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.service.ChatMedicalSessionService;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.MedicalSessionDoctorAdminService;
import ar.com.lodev.medical_manager.component.service.NotificationService;
import ar.com.lodev.medical_manager.exception.MedicalSessionException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.PracticePlaceLoggedException;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.DoctorPracticePlaceAssociation;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.Patient;
import ar.com.lodev.medical_manager.model.Symptom;
import ar.com.lodev.medical_manager.model.dto.MedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.SymptomDTO;
import ar.com.lodev.medical_manager.ui.model.OrderDirection;

@Service
public class MedicalSessionDoctorAdminServiceImpl extends BaseService implements MedicalSessionDoctorAdminService {

	@Autowired
	private MedicalSessionRepository medicalSessionRepository;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private ChatMedicalSessionService chatMedicalSessionService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public boolean hasAccessToSession(long sessionId) {
		Doctor doctor = doctorService.getEntityFromSession();
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		return session.getDoctor().equals(doctor);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void allocate(long sessionId) throws MedicalSessionException, PracticePlaceLoggedException {
		Doctor doctor = doctorService.getEntityFromSession();
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		DoctorPracticePlaceAssociation association = doctor.getPracticeLogged();
		if (!session.getPracticePlace().equals(association.getPracticePlace())) {
			throw new PracticePlaceLoggedException("You need to be logged into the practice place");
		}
		if (!session.isAvailableToBeTaken()) {
			throw new MedicalSessionException("The session has already been taked.");
		}
		session.setAvailableToBeTaken(false);
		session.setDoctor(doctor);

		chatMedicalSessionService.createChatForDoctor(doctor, session);

		Patient patient = session.getPatient();
		if (patient.getGcmId() != null && !patient.getGcmId().isEmpty()) {
			notificationService.notifyDoctorAllocate(doctor, patient.getGcmId());
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deallocate(long sessionId) throws MedicalSessionException, PracticePlaceLoggedException {
		Doctor doctor = doctorService.getEntityFromSession();
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		DoctorPracticePlaceAssociation association = doctor.getPracticeLogged();
		if (!session.getPracticePlace().equals(association.getPracticePlace())) {
			throw new PracticePlaceLoggedException("You need to be logged into the practice place");
		}
		if (!session.isAvailableToBeTaken()) {
			session.setAvailableToBeTaken(true);
			session.setDoctor(null);
		}

		Patient patient = session.getPatient();
		if (patient.getGcmId() != null && !patient.getGcmId().isEmpty()) {
			notificationService.notifyDoctorDeAllocate(doctor, patient.getGcmId());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MedicalSessionDTO find(long sessionId) throws PracticePlaceLoggedException {
		Doctor doctor = doctorService.getEntityFromSession();
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		DoctorPracticePlaceAssociation association = doctor.getPracticeLogged();
		if (!session.getPracticePlace().equals(association.getPracticePlace())) {
			throw new PracticePlaceLoggedException("You need to be logged into the practice place");
		}
		return new MedicalSessionDTO(session, false, false, doctor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MedicalSessionDTO> search(String name, int offset, OrderDirection orderDirection, String columnToOrder)
			throws PracticePlaceException {
		Doctor doctor = doctorService.getEntityFromSession();
		DoctorPracticePlaceAssociation association = doctor.getPracticeLogged();
		if (association == null) {
			throw new PracticePlaceException("You need to be logged into a practice place");
		}
		PageRequest pageRequest = null;
		if (columnToOrder == null || orderDirection == null) {
			// ORDER BY m.open DESC, m.availableToBeTaken DESC
			pageRequest = new PageRequest(offset / limit, limit,
					new Sort(new Order(Direction.DESC, "open"), new Order(Direction.DESC, "availableToBeTaken")));

		} else {
			pageRequest = new PageRequest(offset / limit, limit,
					new Sort(new Order(Direction.valueOf(orderDirection.toString()), columnToOrder)));
		}
		Page<MedicalSession> page = medicalSessionRepository
				.listSessionsFromPracticePlace(association.getPracticePlace().getId(), name, pageRequest);

		List<MedicalSessionDTO> dtos = new ArrayList<MedicalSessionDTO>();
		for (MedicalSession session : page.getContent()) {
			dtos.add(new MedicalSessionDTO(session, false, false, doctor));
		}
		return dtos;
	}

	@Override
	@Transactional(readOnly = true)
	public long countSearchForDoctor(String name) {
		Doctor doctor = doctorService.getEntityFromSession();
		DoctorPracticePlaceAssociation association = doctor.getPracticeLogged();
		long number = medicalSessionRepository.countSessionsFromPracticePlace(association.getPracticePlace().getId(),
				name);
		return number;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SymptomDTO> listSymptoms(long sessionId) throws PracticePlaceException {
		Doctor doctor = doctorService.getEntityFromSession();
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		if (!doctor.getPracticeLogged().getPracticePlace().equals(session.getPracticePlace())) {
			throw new PracticePlaceException("You need to be logged into the practice place");
		}
		List<SymptomDTO> symptomDTOs = new ArrayList<>();
		for (Symptom symp : session.getSymptoms()) {
			symptomDTOs.add(new SymptomDTO(symp));
		}
		return symptomDTOs;
	}
}
