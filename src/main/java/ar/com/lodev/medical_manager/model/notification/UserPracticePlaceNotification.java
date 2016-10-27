package ar.com.lodev.medical_manager.model.notification;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.com.lodev.medical_manager.model.BaseEntity;
import ar.com.lodev.medical_manager.model.User;

//Not being used
public class UserPracticePlaceNotification extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="notification_id")
	private NotificationPracticePlace notification;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	private boolean read;
	
	public UserPracticePlaceNotification(User user, NotificationPracticePlace notification){
		this.notification = notification;
		this.user = user;
		this.read = false;
	}
	
	

	public boolean isRead() {
		return read;
	}



	public void setRead(boolean read) {
		this.read = read;
	}



	public NotificationPracticePlace getNotification() {
		return notification;
	}

	public void setNotification(NotificationPracticePlace notification) {
		this.notification = notification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
