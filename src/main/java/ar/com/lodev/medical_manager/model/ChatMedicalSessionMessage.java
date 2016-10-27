package ar.com.lodev.medical_manager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChatMedicalSessionMessage extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="chat_session_id")
	private ChatMedicalSession chatSession;
	@Column(nullable=false)
	private String message;
	private Date dateCreated;
	private boolean messageUserToPatient;
	
	protected ChatMedicalSessionMessage(){
		
	}
	
	public ChatMedicalSessionMessage(ChatMedicalSession chatSession,String message,
			boolean messageUserToPatient){
		this.message = message;
		this.chatSession = chatSession;
		this.dateCreated = new Date();
		this.messageUserToPatient = messageUserToPatient;
	}
	
	
	public boolean isMessageUserToPatient() {
		return messageUserToPatient;
	}

	public void setMessageUserToPatient(boolean messageUserToPatient) {
		this.messageUserToPatient = messageUserToPatient;
	}

	public ChatMedicalSession getChatSession() {
		return chatSession;
	}

	public void setChatSession(ChatMedicalSession chatSession) {
		this.chatSession = chatSession;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
	
}
