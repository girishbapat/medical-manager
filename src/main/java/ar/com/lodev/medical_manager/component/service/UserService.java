package ar.com.lodev.medical_manager.component.service;

import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.User;

public interface UserService {

	boolean usernameUnique(String username);

	boolean emailUnique(String email);

	User create(String username, String password, String roleName, String email)
			throws UserException;

	void forgotPassword(String email) throws UserException;

	void forgotUsername(String email) throws UserException;

}
