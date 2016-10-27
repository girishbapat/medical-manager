package ar.com.lodev.medical_manager.exception;

public class MedicalSessionException extends Exception{
	
	public MedicalSessionException(String message){
		super(message);
	}
	
	public MedicalSessionException(Throwable e){
		super(e);
	}

}
