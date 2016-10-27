package ar.com.lodev.medical_manager.model.notification;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.com.lodev.medical_manager.model.PracticePlace;

//Not being used
public abstract class NotificationPracticePlace{
	
	@ManyToOne
	@JoinColumn(name="practice_id")
	private PracticePlace practicePlace;
	private Date dateCreated;
	
	protected NotificationPracticePlace(){
		
	}
	
	public NotificationPracticePlace(PracticePlace practicePlace){
		this.practicePlace = practicePlace;
		this.dateCreated = new Date();
	}
	
	public abstract String getContentDescription();
	
	public PracticePlace getPracticePlace() {
		return practicePlace;
	}
	public void setPracticePlace(PracticePlace practicePlace) {
		this.practicePlace = practicePlace;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	
}
