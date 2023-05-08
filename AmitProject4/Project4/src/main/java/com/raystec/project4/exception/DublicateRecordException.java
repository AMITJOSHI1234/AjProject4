package com.raystec.project4.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 * 
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class DublicateRecordException extends Exception {
	/**
     * @param msg
     *            error message
     */
	public DublicateRecordException(String msg) {
		super(msg);
	}
}
