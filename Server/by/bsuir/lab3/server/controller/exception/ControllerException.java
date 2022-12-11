package by.bsuir.lab3.server.controller.exception;

public class ControllerException extends Exception {

	public ControllerException(String message, Exception inntException){
		super(message, inntException);
	}
}
