package ar.com.lodev.medical_manager.model.dto;

import java.util.ArrayList;
import java.util.List;

import ar.com.lodev.medical_manager.model.ChatMedicalSession;
import ar.com.lodev.medical_manager.model.ChatMedicalSessionMessage;

public class ChatMedicalSessionDTO extends BaseEntityDTO{
	
	private PracticePlaceDTO practicePlace;
	private DoctorDTO doctor;
	private MedicalSessionDTO session;	
	private List<ChatMedicalSessionMessageDTO> messages;
	
	public ChatMedicalSessionDTO(ChatMedicalSession chatSession){
		super(chatSession);
		if(chatSession.getPracticePlace() != null){
			this.practicePlace = new PracticePlaceDTO(chatSession.getPracticePlace());
		}
		if(chatSession.getDoctor() != null){
			this.doctor = new DoctorDTO(chatSession.getDoctor());
		}
		this.messages = new ArrayList<ChatMedicalSessionMessageDTO>();
		if(chatSession.getMessages() != null){
			for(ChatMedicalSessionMessage m : chatSession.getMessages()){
				this.messages.add(new ChatMedicalSessionMessageDTO(m));
			}
		}
		
		this.session = new MedicalSessionDTO(chatSession.getSession(),false);
	}
	
	public ChatMedicalSessionDTO(ChatMedicalSession chatSession,List<ChatMedicalSessionMessage> messages){
		super(chatSession);
		if(chatSession.getPracticePlace() != null){
			this.practicePlace = new PracticePlaceDTO(chatSession.getPracticePlace());
		}
		if(chatSession.getDoctor() != null){
			this.doctor = new DoctorDTO(chatSession.getDoctor());
		}
		this.messages = new ArrayList<ChatMedicalSessionMessageDTO>();
		this.session = new MedicalSessionDTO(chatSession.getSession(),false);
		for(ChatMedicalSessionMessage m : messages){
			this.messages.add(new ChatMedicalSessionMessageDTO(m));
		}
	}

	public PracticePlaceDTO getPracticePlace() {
		return practicePlace;
	}

	public void setPracticePlace(PracticePlaceDTO practicePlace) {
		this.practicePlace = practicePlace;
	}

	public DoctorDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}

	public MedicalSessionDTO getSession() {
		return session;
	}

	public void setSession(MedicalSessionDTO session) {
		this.session = session;
	}

	public List<ChatMedicalSessionMessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMedicalSessionMessageDTO> messages) {
		this.messages = messages;
	}
	
	

}
