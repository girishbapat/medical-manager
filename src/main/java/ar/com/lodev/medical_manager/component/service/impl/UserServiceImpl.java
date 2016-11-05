package ar.com.lodev.medical_manager.component.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.RoleRepository;
import ar.com.lodev.medical_manager.component.dao.UserRepository;
import ar.com.lodev.medical_manager.component.service.EmailService;
import ar.com.lodev.medical_manager.component.service.UserService;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.Role;
import ar.com.lodev.medical_manager.model.User;

@Service
public class UserServiceImpl extends BaseService implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@Transactional
	public void forgotPassword(String email) throws UserException{
		User user = userRepository.findByEmail(email);
		if(user == null){
			throw new UserException("User with email: "+email+" does not exist");
		}
        String password = RandomStringUtils.random(6, true, true);
        user.setPassword(bCryptPasswordEncoder.encode(password));
		emailService.enviarEmail(user.getEmail(), "ConsultPal - password changed"
				, "You have requested a new password. Your new password is: "+password, null,null,null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void forgotUsername(String email) throws UserException{
		User user = userRepository.findByEmail(email);
		if(user == null){
			throw new UserException("User with email: "+email+" does not exist");
		}
		emailService.enviarEmail(user.getEmail(), "ConsultPal - username requested"
				, "You have requested your username. Your username is: "+user.getUsername(), null,null,null);
	}
	
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public User create(String username,String password,String email,String roleName) throws UserException{
		username = username.replaceAll("\\s+","");
		if(!usernameUnique(username)){
			throw new UserException("username already exist");
		}
		if(!emailUnique(email)){
			throw new UserException("email already exist");
		}
		Role role = roleRepository.findByName(roleName);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		User user = new User(username, bCryptPasswordEncoder.encode(password),email, roles);
		user = userRepository.save(user);
		return user;
	}
	
	
	@Override
	public boolean usernameUnique(String username){
		User user = userRepository.findByUsername(username);
		User userlogged = getUserLogged();
		if(userlogged != null){
			return userlogged.equals(user);
		}
		return user == null;
	}
	
	@Override
	public boolean emailUnique(String email){
		User user = userRepository.findByEmail(email);
		User userlogged = getUserLogged();
		if(userlogged != null){
			return userlogged.equals(user);
		}
		return user == null;
	}

}
