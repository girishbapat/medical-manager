package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.DoctorPracticePlaceAssociation;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;


public class DoctorPracticePlaceAssociationDTO extends BaseEntityDTO{
	
	private DoctorDTO doctor;
	private PracticePlaceDTO practicePlace;
	private PracticePlaceDoctorRequestStatus status;
	
	public DoctorPracticePlaceAssociationDTO(DoctorPracticePlaceAssociation association){
		super(association);
		this.doctor = new DoctorDTO(association.getDoctor());
		this.practicePlace = new PracticePlaceDTO(association.getPracticePlace());
		this.status = association.getStatus();
	}
	
	/**
	 * Use it when there is no request for a {@link PracticePlace}
	 * @param practicePlace
	 */
	public DoctorPracticePlaceAssociationDTO(PracticePlace practicePlace){
		this.practicePlace = new PracticePlaceDTO(practicePlace);
		this.status = PracticePlaceDoctorRequestStatus.NO_REQUEST;
	}
	
	public PracticePlaceDoctorRequestStatus getStatus() {
		return status;
	}

	public void setStatus(PracticePlaceDoctorRequestStatus status) {
		this.status = status;
	}

	public DoctorDTO getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}
	public PracticePlaceDTO getPracticePlace() {
		return practicePlace;
	}
	public void setPracticePlace(PracticePlaceDTO practicePlace) {
		this.practicePlace = practicePlace;
	}
	
	
}
