package com.raystec.project4.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * 
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class ApplicationException extends Exception{

	/**
     * @param msg
     *            : Error message
     */
	
	public ApplicationException(String msg) {
		super(msg);
	}
	
}
