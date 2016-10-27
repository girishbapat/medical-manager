package ar.com.lodev.medical_manager.component.service;

import java.util.List;

import ar.com.lodev.medical_manager.exception.MedicalSessionException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.PracticePlaceLoggedException;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.dto.MedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.SymptomDTO;
import ar.com.lodev.medical_manager.ui.model.OrderDirection;

/**
 * It the point which a {@link Doctor} can manage the {@link MedicalSession}
 * @author leandro
 *
 */
public interface MedicalSessionDoctorAdminService {

	long countSearchForDoctor(String name);

	List<SymptomDTO> listSymptoms(long sessionId) throws PracticePlaceException;

	void allocate(long sessionId) throws MedicalSessionException,
			PracticePlaceLoggedException;

	boolean hasAccessToSession(long sessionId);

	MedicalSessionDTO find(long sessionId) throws PracticePlaceLoggedException;

	List<MedicalSessionDTO> search(String name, int offset,
			OrderDirection orderDirection, String columnToOrder)
			throws PracticePlaceException;

}
