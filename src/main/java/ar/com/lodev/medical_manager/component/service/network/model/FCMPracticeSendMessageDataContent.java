package ar.com.lodev.medical_manager.component.service.network.model;

public class FCMPracticeSendMessageDataContent {
	
	private long practicePlaceId;
	private String practiceName;
	private String message;
	private long dateSent;
	private String type;
	
	public FCMPracticeSendMessageDataContent(long practicePlaceId,String practiceName,
			String message,long dateSent){
		this.practicePlaceId = practicePlaceId;
		this.practiceName = practiceName;
		this.message = message;
		this.dateSent = dateSent;
		this.type = "message";
	}
	
	

	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public long getPracticePlaceId() {
		return practicePlaceId;
	}

	public void setPracticePlaceId(long practicePlaceId) {
		this.practicePlaceId = practicePlaceId;
	}

	public String getPracticeName() {
		return practiceName;
	}

	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDateSent() {
		return dateSent;
	}

	public void setDateSent(long dateSent) {
		this.dateSent = dateSent;
	}
	
	

}
