package by.bsuir.lab3.server.dao.exception;

public class DaoException extends Exception {

	public DaoException(String message, Exception inntException){
		super(message, inntException);
	}
}
