package ar.com.lodev.medical_manager.exception;

public class PracticePlaceException extends Exception{
	
	public PracticePlaceException(String message){
		super(message);
	}
	
	public PracticePlaceException(Throwable e){
		super(e);
	}

}
