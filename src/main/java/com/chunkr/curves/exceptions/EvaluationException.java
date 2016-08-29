package com.chunkr.curves.exceptions;

public class EvaluationException extends Exception {

	private static final long serialVersionUID = 1983473143890508871L;

	public EvaluationException() {
		super();
	}
 	
 	public EvaluationException(String message) {
 		super(message);
 	}
 	
 	public EvaluationException(String message, Throwable cause) {
 		super(message, cause);
 	}
 	
 	public EvaluationException(Throwable cause) {
 		super(cause);
 	}
 	
}
