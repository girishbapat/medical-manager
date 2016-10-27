package ar.com.lodev.medical_manager.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ChatMedicalSession extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="practice_id")
	private PracticePlace practicePlace;
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	@ManyToOne(optional=false)
	@JoinColumn(name="session_id")
	private MedicalSession session;	
	@OneToMany(mappedBy="chatSession")
	private List<ChatMedicalSessionMessage> messages;
	
	
	protected ChatMedicalSession(){
		
	}
	
	public ChatMedicalSession(PracticePlace practicePlace,MedicalSession session){
		this.practicePlace = practicePlace;
		this.session = session;
	}
	
	public ChatMedicalSession(Doctor doctor,MedicalSession session){
		this.doctor = doctor;
		this.session = session;
	}
	
	public PracticePlace getPracticePlace() {
		return practicePlace;
	}

	public void setPracticePlace(PracticePlace practicePlace) {
		this.practicePlace = practicePlace;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public MedicalSession getSession() {
		return session;
	}

	public void setSession(MedicalSession session) {
		this.session = session;
	}

	public List<ChatMedicalSessionMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMedicalSessionMessage> messages) {
		this.messages = messages;
	}
	
	

}
