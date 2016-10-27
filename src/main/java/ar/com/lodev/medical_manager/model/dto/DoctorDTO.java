package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.Doctor;

public class DoctorDTO extends BaseEntityDTO{
	
	private String name;
	private String lastname;
	private String description;
	private ImageEntityDTO imageProfile;
	private String imageProfileUrl;
	private PracticePlaceDTO practiceLogged;
	private String welcomeMessage;
	
	public DoctorDTO(Doctor doctor){
		super(doctor);
		this.name = doctor.getName();
		this.lastname = doctor.getLastname();
		this.description = doctor.getDescription();
		if(doctor.getImageProfile() != null){
			this.imageProfile = new ImageEntityDTO(doctor.getImageProfile(), false);
		}
		if(doctor.getPracticeLogged() != null){
			this.practiceLogged = new PracticePlaceDTO(doctor.getPracticeLogged().getPracticePlace());
		}
		this.imageProfileUrl = "download/image/"+imageProfile.getId();
		this.welcomeMessage = doctor.getWelcomeMessage();
	}
	
	public String getFullName(){
		return name +", "+lastname;
	}

	public String getWelcomeMessage() {
		return welcomeMessage;
	}



	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}



	public String getImageProfileUrl() {
		return imageProfileUrl;
	}



	public void setImageProfileUrl(String imageProfileUrl) {
		this.imageProfileUrl = imageProfileUrl;
	}



	public PracticePlaceDTO getPracticeLogged() {
		return practiceLogged;
	}



	public void setPracticeLogged(PracticePlaceDTO practiceLogged) {
		this.practiceLogged = practiceLogged;
	}



	public ImageEntityDTO getImageProfile() {
		return imageProfile;
	}



	public void setImageProfile(ImageEntityDTO imageProfile) {
		this.imageProfile = imageProfile;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
