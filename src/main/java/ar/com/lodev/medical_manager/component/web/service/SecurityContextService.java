package ar.com.lodev.medical_manager.component.web.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface SecurityContextService {

	void authenticate(String username, String password, HttpServletRequest request);

	Authentication getUser();

}
