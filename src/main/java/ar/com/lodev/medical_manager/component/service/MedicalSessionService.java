package ar.com.lodev.medical_manager.component.service;

import java.util.Date;
import java.util.List;

import ar.com.lodev.medical_manager.exception.MedicalSessionException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.Patient;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.MedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.SymptomDTO;
import ar.com.lodev.medical_manager.ui.model.OrderDirection;

public interface MedicalSessionService {
	
	/**
	 * create a {@link MedicalSession} for a {@link Patient} who has
	 * no {@link User} associated
	 * @param practicePlaceId
	 * @param name
	 * @param lastname
	 * @param dateOfBirth
	 * @param gcmId
	 * @param email
	 * @param doctorId 
	 * @param dateOfAppointment 
	 * @return
	 * @throws PracticePlaceException
	 */
	MedicalSessionDTO createSession(long practicePlaceId, String name,
			String lastname, Date dateOfBirth, String gcmId, String email, Date dateOfAppointment, Long doctorId)
			throws PracticePlaceException;

	List<SymptomDTO> listSymptoms(long sessionId);

	long countSearchForPractice(String name);

	MedicalSessionDTO finish(long sessionId, String token)
			throws MedicalSessionException;


	MedicalSessionDTO find(long sessionId, String token);

	void updateSession(long sessionId, List<SymptomDTO> symptoms,
			List<SymptomDTO> symptomsToDelete, String token)
			throws MedicalSessionException;

	List<MedicalSessionDTO> searchForPractice(String name, int offset,
			OrderDirection orderDirection, String columnToOrder)
			throws PracticePlaceException;

}
