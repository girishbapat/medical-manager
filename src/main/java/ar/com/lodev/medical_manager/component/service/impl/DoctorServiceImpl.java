package ar.com.lodev.medical_manager.component.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.com.lodev.medical_manager.component.dao.DoctorRepository;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.UserService;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.Role;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

@Service
public class DoctorServiceImpl extends BaseService implements DoctorService{
	
	@Autowired
	private UserService userService;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public DoctorDTO create(String username,String password,String name,
			String lastname,String email) throws UserException{
		User user = userService.create(username, password,email, Role.DOCTOR);
		Doctor doctor = new Doctor(name, lastname, user);
		doctor = doctorRepository.save(doctor);
		return new DoctorDTO(doctor);
	}
	
	@Override
	@Transactional
	public DoctorDTO edit(String name,String lastname,String welcomeMessage,
			String description,String password,MultipartFile photo) throws IOException{
		User user = getUserLogged();
		Doctor doctor = doctorRepository.findByUserId(user.getId());
		doctor.setDescription(description);
		doctor.setName(name);
		doctor.setLastname(lastname);
		doctor.setWelcomeMessage(welcomeMessage);
		if(password!=null && !password.isEmpty()){
			doctor.getUser().setPassword(bCryptPasswordEncoder.encode(password));
		}
		if(photo != null && photo.getContentType().contains("image")){
			doctor.getImageProfile().setPhoto(photo.getBytes());
			String extension = photo.getContentType().split("/")[1];
			doctor.getImageProfile().setExtension(extension);
		}
		return new DoctorDTO(doctor);
	}
	
	@Override
	@Transactional(readOnly=true)
	public DoctorDTO getFromSession(){
		User user = getUserLogged();
		Doctor doctor = doctorRepository.findByUserId(user.getId());
		return new DoctorDTO(doctor);
	}

	
	@Override
	@Transactional(readOnly=true)
	public PracticePlaceDTO getPracticeLogged(){
		User user = getUserLogged();
		Doctor doctor = doctorRepository.findByUserId(user.getId());
		if(doctor.getPracticeLogged()!=null){
			return new PracticePlaceDTO(doctor.getPracticeLogged().getPracticePlace());
		}else{
			
		}
		return null;
	}
	
	@Override
	@Transactional
	public Doctor getEntityFromSession(){
		User user = getUserLogged();
		Doctor doctor = doctorRepository.findByUserId(user.getId());
		return doctor;
	}
}
