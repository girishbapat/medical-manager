package ar.com.lodev.medical_manager.ui.model;

public class ValidationResponse {
	
	private boolean valid;
	private String message;
	
	public ValidationResponse(boolean valid,String message){
		this.valid = valid;
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
