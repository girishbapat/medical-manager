package ar.com.lodev.medical_manager.component.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

public interface PracticePlaceService {

	/**
	 * search for {@link PracticePlace} that contains a praticeId like
	 * the one proportioned
	 * @param practiceId
	 * @return
	 */
	List<PracticePlaceDTO> findByPracticeIdContaining(String practiceId);

	/**
	 * List associations in a {@link PracticePlace} considering the user logged.
	 * It must be admin of the PracticePlace.
	 * @param offset
	 * @return
	 */
	List<DoctorPracticePlaceAssociationDTO> listDoctors(int offset);
	
	/**
	 * * count associations in a {@link PracticePlace} considering the user logged.
	 * It must be admin of the PracticePlace.
	 * @return
	 */
	long countDoctors();

	/**
	 * get the {@link PracticePlace} from the {@link User} admin logged
	 * @return
	 */
	PracticePlaceDTO getFromAdmin();

	List<DoctorPracticePlaceAssociationDTO> searchForDoctor(String practiceId, int offset);

	long countSearchForDoctor(String practiceId);

	PracticePlaceDTO create(String username, String practiceId, String practiceName, String password, String email)
			throws UserException, PracticePlaceException;

	PracticePlaceDTO edit(String description, String password,
			MultipartFile photo) throws IOException;

}
