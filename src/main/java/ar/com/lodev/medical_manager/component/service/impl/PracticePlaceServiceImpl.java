package ar.com.lodev.medical_manager.component.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.com.lodev.medical_manager.component.dao.DoctorPracticePlaceRepository;
import ar.com.lodev.medical_manager.component.dao.DoctorRepository;
import ar.com.lodev.medical_manager.component.dao.PracticePlaceRepository;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceAssociationService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.component.service.UserService;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.exception.UserException;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.DoctorPracticePlaceAssociation;
import ar.com.lodev.medical_manager.model.ImageEntity;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;
import ar.com.lodev.medical_manager.model.Role;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;
import ar.com.lodev.medical_manager.model.dto.PracticePlaceDTO;

@Service
public class PracticePlaceServiceImpl extends BaseService implements PracticePlaceService{
	
	@Autowired
	private PracticePlaceRepository practicePlaceRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private DoctorPracticePlaceRepository doctorPlaceRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PracticePlaceAssociationService practicePlaceAssociationService;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@Transactional(readOnly=true)
	public PracticePlaceDTO getFromAdmin(){
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		return new PracticePlaceDTO(practicePlace);
	}
	
	@Override
	@Transactional
	public PracticePlaceDTO edit(String description,String password,MultipartFile photo) throws IOException{
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		practicePlace.setDescription(description);
		if(practicePlace.getImageProfile() == null){
			practicePlace.setImageProfile(new ImageEntity(null,""));
		}
		if(photo != null && photo.getContentType().contains("image")){
			practicePlace.getImageProfile().setPhoto(photo.getBytes());
			String extension = photo.getContentType().split("/")[1];
			practicePlace.getImageProfile().setExtension(extension);
		}
		if(password != null && !password.isEmpty()){
			admin.setPassword(bCryptPasswordEncoder.encode(password));
		}
		return new PracticePlaceDTO(practicePlace);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<DoctorPracticePlaceAssociationDTO> searchForDoctor(String practiceId,int offset){
		Doctor doctor = doctorService.getEntityFromSession();
		List<DoctorPracticePlaceAssociation> associations = doctor.getPracticePlaceAssociations();
		Page<PracticePlace> page = practicePlaceRepository.search(practiceId, new PageRequest(offset/limit, limit));
		List<DoctorPracticePlaceAssociationDTO> dtos = new ArrayList<DoctorPracticePlaceAssociationDTO>(); 
		for(PracticePlace p : page.getContent()){
			DoctorPracticePlaceAssociation assoc = practicePlaceAssociationService.existAssociation(associations, p);
			if(assoc != null){
				dtos.add(new DoctorPracticePlaceAssociationDTO(assoc));
			}else{
				dtos.add(new DoctorPracticePlaceAssociationDTO(p));
			}
		}
		return dtos;
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public long countSearchForDoctor(String practiceId){
		long number = practicePlaceRepository.countSearch(practiceId);
		return number;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<DoctorPracticePlaceAssociationDTO> listDoctors(int offset){
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		Page<DoctorPracticePlaceAssociation> associationsPage = 
				doctorPlaceRepository.listByPracticePlace(practicePlace.getId(), new PageRequest(offset/limit, limit));
		List<DoctorPracticePlaceAssociationDTO> dtos = new ArrayList<DoctorPracticePlaceAssociationDTO>();
		for(DoctorPracticePlaceAssociation association : associationsPage.getContent()){
			dtos.add(new DoctorPracticePlaceAssociationDTO(association));
		}
		
		return dtos;
	}
	
	@Override
	@Transactional(readOnly=true)
	public long countDoctors(){
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		long number = doctorPlaceRepository.countByPracticePlace(practicePlace.getId());
		return number;
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public PracticePlaceDTO create(String username,String practiceId,
			String practiceName,String password,String email) throws UserException, PracticePlaceException{
		User user = userService.create(username, password,email, Role.PRACTICE_ADMIN);
		if(isPracticeIdUnique(practiceId)){
			PracticePlace place = new PracticePlace(user, practiceId, "", "");
			practicePlaceRepository.save(place);
			return new PracticePlaceDTO(place);
		}else{
			throw new PracticePlaceException("Practice ID already exist");
		}
	}
	
	private boolean isPracticeIdUnique(String practiceId){
		PracticePlace practicePlace = practicePlaceRepository.findByPracticeId(practiceId);
		return practicePlace == null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<PracticePlaceDTO> findByPracticeIdContaining(String practiceId){
		List<PracticePlace> practicePlaces = practicePlaceRepository.findByPracticeIdContaining(practiceId);
		List<PracticePlaceDTO> practiceDTOs = new ArrayList<PracticePlaceDTO>();
		for(PracticePlace p : practicePlaces){
			practiceDTOs.add(new PracticePlaceDTO(p));
		}
		return practiceDTOs;
	}

	@Override
	public List<DoctorDTO> listDoctorsByPracticeId(String practiceId) {
		Page<DoctorPracticePlaceAssociation> drPracticePlaceAssociations = 
				doctorPlaceRepository.listByPracticePlace(Long.parseLong(practiceId), null);
		List<DoctorDTO> doctors = new ArrayList<DoctorDTO>();
		for (DoctorPracticePlaceAssociation a : drPracticePlaceAssociations) {
			if(a.getStatus() == PracticePlaceDoctorRequestStatus.APPROVED){
				DoctorDTO d = new DoctorDTO(a.getDoctor());
				if(d.getPracticeLogged() != null){
					doctors.add(d);	
				}	
			}
		}
		return doctors;
	}
	
}
