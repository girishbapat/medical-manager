package ar.com.lodev.medical_manager.component.service.network.model;

public class FCMDoctorSendMessageDataContent {
	
	//{doctor id: long, doctor name: string, message: string, date: string}
	private long doctorId;
	private String doctorName;
	private String message;
	private long dateSent;
	private String type;
		
	public FCMDoctorSendMessageDataContent(long doctorId,String doctorName,
			String message,long dateSent){
		this.doctorId = doctorId;
		this.doctorName = doctorName;
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



	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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
