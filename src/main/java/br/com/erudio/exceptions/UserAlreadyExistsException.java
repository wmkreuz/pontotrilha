package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String ex) {
		super(ex);
	}
}