package com.raystec.project4.exception;

/**
 * RecordNotFoundException thrown when a record not found occurred
 * 
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class RecordNotFoundException extends Exception {
	 /**
     * @param msg
     *            error message
     */
	
	public RecordNotFoundException(String msg) {
		super(msg);
	}
}
