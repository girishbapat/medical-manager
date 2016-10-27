package ar.com.lodev.medical_manager.model.dto;

import java.util.ArrayList;
import java.util.List;

import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.Symptom;

public class MedicalSessionDTO extends BaseEntityDTO{
	
	private PatientDTO patient;
	private PracticePlaceDTO practicePlace;
	private DoctorDTO doctor;
	private Long startDate;
	private Long finishDate;
	private List<SymptomDTO> symptoms;
	private boolean open = true;
	private boolean availableToBeTaken = true;
	private Boolean ownSession;
	private String token;
	
	
	public MedicalSessionDTO(MedicalSession medicalSession,boolean withToken){
		super(medicalSession);
		this.patient = new PatientDTO(medicalSession.getPatient());
		this.practicePlace = new PracticePlaceDTO(medicalSession.getPracticePlace());
		if(medicalSession.getDoctor()!=null){
			this.doctor = new DoctorDTO(medicalSession.getDoctor());
		}
		this.startDate = medicalSession.getStartDate().getTime();
		if(medicalSession.getFinishDate() != null){
			this.finishDate = medicalSession.getFinishDate().getTime();
		}
		if(medicalSession.getSymptoms()!=null){
			this.symptoms = new ArrayList<SymptomDTO>();
			for(Symptom symptom : medicalSession.getSymptoms()){
				this.symptoms.add(new SymptomDTO(symptom));
			}
		}
		this.open = medicalSession.isOpen();
		this.availableToBeTaken = medicalSession.isAvailableToBeTaken();
		if(withToken){
			this.token = medicalSession.getToken();
		}
	}
	
	/**
	 * 
	 * @param medicalSession
	 * @param symptoms indicates if the symptoms are needed
	 * @param practicePlace indicates if the practicePlace is needed
	 */
	public MedicalSessionDTO(MedicalSession medicalSession,boolean symptoms,boolean practicePlace
			,Doctor doctorInSession){
		super(medicalSession);
		this.patient = new PatientDTO(medicalSession.getPatient());
		if(practicePlace){
			this.practicePlace = new PracticePlaceDTO(medicalSession.getPracticePlace());
		}
		if(medicalSession.getDoctor()!=null){
			this.doctor = new DoctorDTO(medicalSession.getDoctor());
		}
		this.startDate = medicalSession.getStartDate().getTime();
		if(medicalSession.getFinishDate() != null){
			this.finishDate = medicalSession.getFinishDate().getTime();
		}
		this.open = medicalSession.isOpen();
		this.availableToBeTaken = medicalSession.isAvailableToBeTaken();
		if(doctorInSession != null && medicalSession.getDoctor() != null){
			if(medicalSession.getDoctor().equals(doctorInSession)){
				this.ownSession = true;
			}else{
				this.ownSession = false;
			}
		}
	}
	
	
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Long finishDate) {
		this.finishDate = finishDate;
	}

	public Boolean getOwnSession() {
		return ownSession;
	}

	public void setOwnSession(Boolean ownSession) {
		this.ownSession = ownSession;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isAvailableToBeTaken() {
		return availableToBeTaken;
	}

	public void setAvailableToBeTaken(boolean availableToBeTaken) {
		this.availableToBeTaken = availableToBeTaken;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public PracticePlaceDTO getPracticePlace() {
		return practicePlace;
	}

	public void setPracticePlace(PracticePlaceDTO practicePlace) {
		this.practicePlace = practicePlace;
	}

	public DoctorDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}


	public List<SymptomDTO> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<SymptomDTO> symptoms) {
		this.symptoms = symptoms;
	}
	
	

}
