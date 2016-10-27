package ar.com.lodev.medical_manager.component.web.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService{
	
	@Autowired
    protected AuthenticationManager myAuthenticationManager;
	
	@Override
	public void authenticate(String username,String password,HttpServletRequest request){
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
				password);
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = myAuthenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication( authenticatedUser );
	}

	@Override
	public Authentication getUser(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
