package by.bsuir.lab3.server.services.exception;

public class ServiceException extends Exception{
	
	public ServiceException(String message, Exception inntException){
		super(message, inntException);
	}
	
}
