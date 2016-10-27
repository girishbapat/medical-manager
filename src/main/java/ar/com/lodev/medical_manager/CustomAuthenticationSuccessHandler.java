package ar.com.lodev.medical_manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ar.com.lodev.medical_manager.model.Role;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Value("${server.contextPath}")
	private String root;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req,
			HttpServletResponse response, Authentication auth) throws IOException,
			ServletException {
		List<GrantedAuthority> auths = (List<GrantedAuthority>) auth.getAuthorities();
		if(auths.get(0).getAuthority().equals(Role.DOCTOR)){
			response.sendRedirect(root+"/doctor/edit");
		}else if(auths.get(0).getAuthority().equals(Role.PRACTICE_ADMIN)){
			response.sendRedirect(root+"/practice-admin/edit");
		}
		//TODO: otherwise throw exception
	}

}
