package com.acumos.elk.exception;

public class ELKException extends Exception {
	 
    private static final long serialVersionUID = 4664456874499611218L;
 
    private String errorCode="Unknown_Exception";
    
    public ELKException(String s) 
    { 
        // Call constructor of parent Exception 
        super(s); 
    } 
 
    public ELKException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
 
    public String getErrorCode(){
        return this.errorCode;
    }
 
}
