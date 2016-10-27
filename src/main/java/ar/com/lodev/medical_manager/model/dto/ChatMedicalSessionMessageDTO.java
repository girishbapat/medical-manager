package ar.com.lodev.medical_manager.model.dto;

import ar.com.lodev.medical_manager.model.ChatMedicalSessionMessage;

public class ChatMedicalSessionMessageDTO extends BaseEntityDTO{
	
	private String message;
	private long dateCreated;
	private boolean messageUserToPatient;
	
	public ChatMedicalSessionMessageDTO(ChatMedicalSessionMessage message){
		super(message);
		this.dateCreated = message.getDateCreated().getTime();
		this.message = message.getMessage();
		this.messageUserToPatient = message.isMessageUserToPatient();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isMessageUserToPatient() {
		return messageUserToPatient;
	}

	public void setMessageUserToPatient(boolean messageUserToPatient) {
		this.messageUserToPatient = messageUserToPatient;
	}

	

}
