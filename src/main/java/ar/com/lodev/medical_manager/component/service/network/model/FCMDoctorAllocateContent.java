package ar.com.lodev.medical_manager.component.service.network.model;

import ar.com.lodev.medical_manager.model.dto.DoctorDTO;

public class FCMDoctorAllocateContent {
	
	private long doctorId;
	private String doctorName;
	private String doctorPicture;
	private String type;
	private String doctorDescription;
	
	public FCMDoctorAllocateContent(DoctorDTO doctor){
		this.doctorId = doctor.getId();
		this.doctorName = doctor.getFullName();
		this.doctorPicture = doctor.getImageProfileUrl();
		this.type = "allocation";
		this.doctorDescription = doctor.getDescription();
	}
	
	

	public String getDoctorDescription() {
		return doctorDescription;
	}



	public void setDoctorDescription(String doctorDescription) {
		this.doctorDescription = doctorDescription;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorPicture() {
		return doctorPicture;
	}

	public void setDoctorPicture(String doctorPicture) {
		this.doctorPicture = doctorPicture;
	}
	
	

}
