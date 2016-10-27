package ar.com.lodev.medical_manager.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * It represents an association between a {@link Doctor} and
 * a {@link PracticePlace}. The Doctor can only interact with 
 * patients through this entity.
 * @author leandro
 *
 */
@Entity
public class DoctorPracticePlaceAssociation extends BaseEntity{

	@ManyToOne(optional=false)
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	@ManyToOne(optional=false)
	@JoinColumn(name="practice_place_id")
	private PracticePlace practicePlace;
	private PracticePlaceDoctorRequestStatus status;
	
	protected DoctorPracticePlaceAssociation(){
		
	}
	
	
	public DoctorPracticePlaceAssociation(Doctor doctor,PracticePlace practicePlace){
		this.doctor = doctor;
		this.practicePlace = practicePlace;
		this.status = PracticePlaceDoctorRequestStatus.ON_HOLD;
	}
	
	public PracticePlaceDoctorRequestStatus getStatus() {
		return status;
	}



	public void setStatus(PracticePlaceDoctorRequestStatus status) {
		this.status = status;
	}



	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public PracticePlace getPracticePlace() {
		return practicePlace;
	}
	public void setPracticePlace(PracticePlace practicePlace) {
		this.practicePlace = practicePlace;
	}	
	
}
