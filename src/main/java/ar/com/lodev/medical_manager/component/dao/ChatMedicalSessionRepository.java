package ar.com.lodev.medical_manager.component.dao;

import org.springframework.data.repository.CrudRepository;

import ar.com.lodev.medical_manager.model.ChatMedicalSession;

public interface ChatMedicalSessionRepository extends CrudRepository<ChatMedicalSession, Long>{
	
	ChatMedicalSession findByDoctorIdAndSessionId(long doctorId,long sessionId);
	
	ChatMedicalSession findByPracticePlaceIdAndSessionId(long practicePlaceId,long sessionId);
	
}
