package ar.com.lodev.medical_manager.model.notification;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.com.lodev.medical_manager.model.MedicalSession;

//Not being used
public class MedicalSessionNotification extends NotificationPracticePlace{
	
	@ManyToOne
	@JoinColumn(name="session_id")
	private MedicalSession medicalSession;
	private MedicalSessionNotificationType medicalSessionNotificationType;
	
	protected MedicalSessionNotification(){
		
	}
	
	public MedicalSessionNotification(MedicalSession medicalSession,MedicalSessionNotificationType type){
		super(medicalSession.getPracticePlace());
		this.medicalSession = medicalSession;
		this.medicalSessionNotificationType = type;
	}
	
	

	public MedicalSessionNotificationType getMedicalSessionNotificationType() {
		return medicalSessionNotificationType;
	}

	public void setMedicalSessionNotificationType(
			MedicalSessionNotificationType medicalSessionNotificationType) {
		this.medicalSessionNotificationType = medicalSessionNotificationType;
	}

	public MedicalSession getMedicalSession() {
		return medicalSession;
	}

	public void setMedicalSession(MedicalSession medicalSession) {
		this.medicalSession = medicalSession;
	}

	@Override
	public String getContentDescription() {
		if(medicalSessionNotificationType.equals(MedicalSessionNotificationType.LOGIN)){
			return "New patient logged: "+medicalSession.getPatient().getFullName();
		}
		return "";
	}
	
	
}
