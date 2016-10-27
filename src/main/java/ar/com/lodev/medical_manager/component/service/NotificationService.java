package ar.com.lodev.medical_manager.component.service;


import java.util.Date;

import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.PracticePlace;

public interface NotificationService {

	void notifyNewMessage(Doctor doctor, String fcmId, String message, Date dateOfMessage);

	void notifyNewMessage(PracticePlace practicePlace, String fcmId,
			String message, Date dateOfMessage);

	void notifyDoctorAllocate(Doctor doctor, String fcmId);


}
