package ar.com.lodev.medical_manager.component.service;

import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.dto.ChatMedicalSessionDTO;

public interface ChatMedicalSessionService {

	ChatMedicalSessionDTO findChatFromDoctor(long sessionId);

	void createMessageFromDoctor(long chatSessionId, String message);

	void createChatForDoctor(Doctor doctor, MedicalSession session);

	void createChatForPractice(MedicalSession session);

	ChatMedicalSessionDTO findChatFromPractice(long sessionId);

	void createMessageFromPractice(long chatSessionId, String message);

}
