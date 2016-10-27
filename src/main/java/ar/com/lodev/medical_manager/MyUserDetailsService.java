package ar.com.lodev.medical_manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.UserRepository;
import ar.com.lodev.medical_manager.model.Role;
import ar.com.lodev.medical_manager.model.User;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("username not found");
		}
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
		
		return buildUserForAuthentication(user, authorities);
	}
	
	
	
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
				user.isActivationState(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for(Role role : roles){
			setAuths.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}
