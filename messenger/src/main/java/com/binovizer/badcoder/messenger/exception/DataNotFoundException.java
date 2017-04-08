package com.binovizer.badcoder.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2882673698237311374L;
	
	public DataNotFoundException(String message){
		super(message);
	}
	
}
