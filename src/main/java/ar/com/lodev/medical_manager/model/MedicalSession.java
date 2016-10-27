package ar.com.lodev.medical_manager.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * It's a session where a {@link Patient} start his activity
 * writing his symptoms.
 * @author leandro
 *
 */
@Entity
public class MedicalSession extends BaseEntity{
	
	@ManyToOne(optional=false)
	@JoinColumn(name="patient_id")
	private Patient patient;
	@ManyToOne(optional=false)
	@JoinColumn(name="practice_place_id")
	private PracticePlace practicePlace;
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	private Date startDate;
	private Date finishDate;
	@OneToMany(mappedBy="session",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Symptom> symptoms;
	private boolean open = true;
	private boolean availableToBeTaken = true;
	private String token;
	
	protected MedicalSession(){
		
	}
	
	public MedicalSession(Patient patient,PracticePlace practicePlace,String token){
		this.patient = patient;
		this.practicePlace = practicePlace;
		this.startDate = new Date();
		this.token = token;
	}
	
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public PracticePlace getPracticePlace() {
		return practicePlace;
	}
	public void setPracticePlace(PracticePlace practicePlace) {
		this.practicePlace = practicePlace;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	

}
