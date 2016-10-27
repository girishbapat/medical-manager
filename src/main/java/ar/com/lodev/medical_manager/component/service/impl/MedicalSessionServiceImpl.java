package ar.com.lodev.medical_manager.component.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.dao.PatientRepository;
import ar.com.lodev.medical_manager.component.dao.PracticePlaceRepository;
import ar.com.lodev.medical_manager.component.service.MedicalSessionService;
import ar.com.lodev.medical_manager.exception.MedicalSessionException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.Patient;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.Symptom;
import ar.com.lodev.medical_manager.model.User;
import ar.com.lodev.medical_manager.model.dto.MedicalSessionDTO;
import ar.com.lodev.medical_manager.model.dto.SymptomDTO;
import ar.com.lodev.medical_manager.ui.model.OrderDirection;

@Service
public class MedicalSessionServiceImpl extends BaseService implements MedicalSessionService{
	
	@Autowired
	private MedicalSessionRepository medicalSessionRepository;
	@Autowired
	private PracticePlaceRepository practicePlaceRepository;
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	@Transactional(readOnly=true)
	public MedicalSessionDTO find(long sessionId,String token){
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		if(session.getToken() != null && !session.getToken().equals(token)){
			throw new SecurityException("Unauthorized to access to this information");
		}
		return new MedicalSessionDTO(session,true);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public MedicalSessionDTO finish(long sessionId,String token) throws MedicalSessionException{
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		if(session == null){
			throw new MedicalSessionException("Medical Session not found");
		}
		if(session.getToken() != null && !session.getToken().equals(token)){
			throw new SecurityException("Unauthorized to access to this information");
		}
		session.setFinishDate(new Date());
		session.setOpen(false);
		return new MedicalSessionDTO(session,true);
	}
	
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public MedicalSessionDTO createSession(long practicePlaceId,String name,String lastname,
			Date dateOfBirth,String gcmId,String email) throws PracticePlaceException{
		PracticePlace practicePlace = practicePlaceRepository.findOne(practicePlaceId);
		if(practicePlace == null){
			throw new PracticePlaceException("Practice Place not found");
		}
		Patient patient = new Patient(name, lastname, dateOfBirth,gcmId,email);
		patient = patientRepository.save(patient);
		MedicalSession session = new MedicalSession(patient, practicePlace,RandomStringUtils.random(6, true, true));
		session = medicalSessionRepository.save(session);
				
		return new MedicalSessionDTO(session,true);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void updateSession(long sessionId,List<SymptomDTO> symptoms,List<SymptomDTO> symptomsToDelete,
			String token) throws MedicalSessionException{
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		if(session == null){
			throw new MedicalSessionException("Medical Session not found");
		}
		if(session.getToken() != null && !session.getToken().equals(token)){
			throw new SecurityException("Unauthorized to access to this information");
		}
		if(symptomsToDelete != null){
			cleanSymptoms(symptomsToDelete, session);
		}
		Iterator<SymptomDTO> iterator = symptoms.iterator();
		while(iterator.hasNext()){
			//only update symptoms
			SymptomDTO symptomDTO = iterator.next();
			if(symptomDTO.getId() != null){
				for(Symptom symptom : session.getSymptoms()){
					if(symptom.getId().equals(symptomDTO.getId())){
						symptom.update(symptomDTO);
						//update the list
						iterator.remove();
						break;
					}
				}
			}
		}
		//for the symptoms that remain create new symptoms for the session
		for(SymptomDTO dto : symptoms){
			Symptom symptom = new Symptom(session,dto.getDescription(),
					dto.getAnswer1(), dto.getAnswer2(),dto.getPriority());
			session.getSymptoms().add(symptom);
		}
	}
	
	private MedicalSession cleanSymptoms(List<SymptomDTO> symptomsToDelete,MedicalSession session){
		Iterator<Symptom> iterator = session.getSymptoms().iterator();
		while(iterator.hasNext()){
			Symptom symp = iterator.next();
			for(SymptomDTO sympDTO : symptomsToDelete){
				if(sympDTO.getId().equals(symp.getId())){
					iterator.remove();
					break;
				}
			}
		}
		return session;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<SymptomDTO> listSymptoms(long sessionId){
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		if(!session.getPracticePlace().equals(practicePlace)){
			throw new SecurityException("You don't have access to this information");
		}
		List<SymptomDTO> symptomDTOs = new ArrayList<>();
		for(Symptom symp : session.getSymptoms()){
			symptomDTOs.add(new SymptomDTO(symp));
		}
		return symptomDTOs;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<MedicalSessionDTO> searchForPractice(String name,int offset,
			OrderDirection orderDirection,String columnToOrder) throws PracticePlaceException{
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		
		PageRequest pageRequest = null;
		if(columnToOrder == null || orderDirection == null){
			//ORDER BY m.open DESC, m.availableToBeTaken DESC
			pageRequest = new PageRequest(offset/limit, limit,new Sort(
					new Order(Direction.DESC, "open"), 
				    new Order(Direction.DESC, "availableToBeTaken")));
			
		}else{
			pageRequest = new PageRequest(offset/limit, limit,new Sort(
					new Order(Direction.valueOf(orderDirection.toString()), columnToOrder)));
		}
		Page<MedicalSession> page = medicalSessionRepository
				.listSessionsFromPracticePlace(practicePlace.getId(),name, pageRequest);
		
		List<MedicalSessionDTO> dtos = new ArrayList<MedicalSessionDTO>(); 
		for(MedicalSession session : page.getContent()){
			dtos.add(new MedicalSessionDTO(session, false, false,null));
		}
		return dtos;
	}
	
	@Override
	@Transactional(readOnly=true)
	public long countSearchForPractice(String name){
		User admin = getUserLogged();
		PracticePlace practicePlace = practicePlaceRepository.findByAdminId(admin.getId());
		long number = medicalSessionRepository
				.countSessionsFromPracticePlace(practicePlace.getId(),name);
		return number;
	}
	
}
