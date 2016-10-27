package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.notification.NotificationPracticePlace;

public class NotificationPracticePlaceDTO extends BaseEntityDTO{
	
	private long dateCreated;
	private String description;
	
	public NotificationPracticePlaceDTO(NotificationPracticePlace notification){
		this.dateCreated = notification.getDateCreated().getTime();
		this.description = notification.getContentDescription();
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
