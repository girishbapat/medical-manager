package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.PracticePlace;

public class PracticePlaceDTO extends BaseEntityDTO{
	
	private String practiceId;
	private String description;
	private ImageEntityDTO imageProfile;
	private String imageProfileUrl;
	
	public PracticePlaceDTO(PracticePlace practicePlace){
		super(practicePlace);
		this.practiceId = practicePlace.getPracticeId();
		this.description = practicePlace.getDescription();
		if(practicePlace.getImageProfile() != null){
			this.imageProfile = new ImageEntityDTO(practicePlace.getImageProfile(), false);
		}
		this.imageProfileUrl = "download/image/"+imageProfile.getId();
	}
	
	

	public String getImageProfileUrl() {
		return imageProfileUrl;
	}



	public void setImageProfileUrl(String imageProfileUrl) {
		this.imageProfileUrl = imageProfileUrl;
	}



	public ImageEntityDTO getImageProfile() {
		return imageProfile;
	}



	public void setImageProfile(ImageEntityDTO imageProfile) {
		this.imageProfile = imageProfile;
	}



	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
