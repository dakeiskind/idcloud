package com.h3c.idcloud.infrastructure.common.exception;

public class ServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String msg, Throwable cause){  
        super(msg, cause);  
    }  
    public ServiceException(Throwable cause){  
        super(cause);  
    }
}
