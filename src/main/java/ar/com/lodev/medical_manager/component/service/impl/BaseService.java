package ar.com.lodev.medical_manager.component.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ar.com.lodev.medical_manager.component.dao.UserRepository;
import ar.com.lodev.medical_manager.model.User;

public abstract class BaseService {

	@Value("${app.dao.query.limit}")
	protected int limit;
	
	@Autowired
	protected UserRepository userRepository;
	
	/**
	 * get the user from the security context
	 * @return
	 */
	User getUserLogged(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());
		return user;
	}
	
}
