package com.pachube.jpachube;

public class PachubeException extends Exception {
	
	/**
     * 
     */
    private static final long serialVersionUID = -4475774556609467087L;
    
    /**
	 * Error message: This is a HTTP status code retrieved from a failed http request
	 */
	public String errorMessage;

	public PachubeException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
}
