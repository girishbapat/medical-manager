package ar.com.lodev.medical_manager.component.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

public interface DoctorService {

	/**
	 * get the {@link Doctor} from the {@link User} logged
	 * @return
	 */
	DoctorDTO getFromSession();

	/**
	 * get the {@link Doctor} from the {@link User} logged. It return a entity from the database
	 * @return
	 */
	Doctor getEntityFromSession();

	PracticePlaceDTO getPracticeLogged();

	DoctorDTO create(String username, String password, String name, String lastname, String email) throws UserException;

	DoctorDTO edit(String name, String lastname, String welcomeMessage,
			String description, String password, MultipartFile photo)
			throws IOException;

	boolean hasPermissionToListDoctor(Long doctorId, Long practiceId);

}
