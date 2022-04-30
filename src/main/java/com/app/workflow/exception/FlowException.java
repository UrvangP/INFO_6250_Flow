package com.app.workflow.exception;

public class FlowException extends Exception {

	public FlowException(String message)
	{
		super(message);
	}
	
	public FlowException(String message, Throwable cause)
	{
		super(message,cause);
	}
}