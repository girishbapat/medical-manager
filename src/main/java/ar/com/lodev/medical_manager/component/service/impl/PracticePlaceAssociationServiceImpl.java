package ar.com.lodev.medical_manager.component.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.DoctorPracticePlaceRepository;
import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.dao.PracticePlaceRepository;
import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceAssociationService;
import ar.com.lodev.medical_manager.exception.DoctorPlaceAssociationException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.DoctorPracticePlaceAssociation;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;

@Service
public class PracticePlaceAssociationServiceImpl extends BaseService implements PracticePlaceAssociationService{
	
	@Autowired
	private DoctorPracticePlaceRepository doctorPlaceRepository;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PracticePlaceRepository practicePlaceRepository;
	@Autowired
	private MedicalSessionRepository medicalSessionRepository;
	
	@Override
	@Transactional
	public DoctorDTO login(long practiceAssociationId) throws DoctorPlaceAssociationException, PracticePlaceException{
		Doctor doctor = doctorService.getEntityFromSession();
		if(doctor.getPracticeLogged()!=null){
			throw new PracticePlaceException("you need to log out from the present practice place");
		}
		DoctorPracticePlaceAssociation association = doctorPlaceRepository.findOne(practiceAssociationId);
		if(association == null){
			throw new DoctorPlaceAssociationException("Association  not found");
		}
		if(!association.getStatus().equals(PracticePlaceDoctorRequestStatus.APPROVED)){
			throw new DoctorPlaceAssociationException("Association  not approved");
		}
		doctor.setPracticeLogged(association);
		return new DoctorDTO(doctor);
	}
	
	@Override
	@Transactional
	public DoctorDTO logout(){
		Doctor doctor = doctorService.getEntityFromSession();
		doctor.setPracticeLogged(null);
		return new DoctorDTO(doctor);
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<DoctorPracticePlaceAssociationDTO> search(int offset){
		DoctorDTO doctor = doctorService.getFromSession();
		Page<DoctorPracticePlaceAssociation> page = doctorPlaceRepository
				.listByDoctor(doctor.getId(),PracticePlaceDoctorRequestStatus.APPROVED, new PageRequest(offset/limit, limit));
		List<DoctorPracticePlaceAssociationDTO> dtos = new ArrayList<DoctorPracticePlaceAssociationDTO>();
		for(DoctorPracticePlaceAssociation association : page.getContent()){
			dtos.add(new DoctorPracticePlaceAssociationDTO(association));
		}
		return dtos;
	}
	
	@Override
	@Transactional(readOnly=true)
	public long countSearch(){
		DoctorDTO doctor = doctorService.getFromSession();
		long number = doctorPlaceRepository.countByByDoctor(doctor.getId(),PracticePlaceDoctorRequestStatus.APPROVED);
		return number;
	}
	
	@Override
	@Transactional
	public DoctorPracticePlaceAssociationDTO updateRequestStatus(long associationId,
			PracticePlaceDoctorRequestStatus status) throws DoctorPlaceAssociationException{
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		DoctorPracticePlaceAssociation association = doctorPlaceRepository.findOne(associationId);
		if(!association.getPracticePlace().equals(practicePlace)){
			throw new DoctorPlaceAssociationException("the association is not from the practicePlace in session");
		}
		if(status.equals(PracticePlaceDoctorRequestStatus.DENIED)){
			List<MedicalSession> sessions = medicalSessionRepository.listDoctorForPracticePlaceFromMedicalSessions(association.getDoctor().getId(), practicePlace.getId());
			for (MedicalSession medicalSession : sessions) {
				medicalSession.setDoctor(null);
				medicalSession.setAvailableToBeTaken(true);
			}
		}
		association.setStatus(status);
		return new DoctorPracticePlaceAssociationDTO(association);
	}
	
	@Override
	@Transactional
	public DoctorPracticePlaceAssociationDTO createRequest(long practiceId) throws DoctorPlaceAssociationException{
		Doctor doctor = doctorService.getEntityFromSession();
		DoctorPracticePlaceAssociation association = doctorPlaceRepository
				.findByDoctorIdAndPracticePlaceId(doctor.getId(), practiceId);
		if(association != null){
			throw new DoctorPlaceAssociationException("the request already exist");
		}
		PracticePlace practicePlace = practicePlaceRepository.findOne(practiceId);
		association = new DoctorPracticePlaceAssociation(doctor, practicePlace);
		association = doctorPlaceRepository.save(association);
		return new DoctorPracticePlaceAssociationDTO(association);
	}
	
	@Override	
	@Transactional
	public void deleteRequestFromDoctor(long associationId) throws DoctorPlaceAssociationException{
		Doctor doctor = doctorService.getEntityFromSession();
		boolean delete = false;
		for(DoctorPracticePlaceAssociation assoc : doctor.getPracticePlaceAssociations()){
			if(assoc.getId().equals(associationId)){
				delete = true;
				break;
			}
		}
		if(delete){
			doctorPlaceRepository.delete(associationId);
		}else{
			throw new DoctorPlaceAssociationException("the association is not from the doctor logged");
		}
	}
	
	@Override
	public DoctorPracticePlaceAssociation existAssociation(List<DoctorPracticePlaceAssociation> associations,PracticePlace practicePlace){
		for(DoctorPracticePlaceAssociation association : associations){
			if(association.getPracticePlace().equals(practicePlace)){
				return association;
			}
		}
		return null;
	}
	
	
	
}
