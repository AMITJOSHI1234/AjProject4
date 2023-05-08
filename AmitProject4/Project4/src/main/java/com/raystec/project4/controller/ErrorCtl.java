package com.raystec.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.project4.util.ServletUtility;
/**
 * @author Amit Joshi
 *
 */

public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
    
	/** The log. */
	private static Logger log = Logger.getLogger(ErrorCtl.class);

	/**
	 * Contains Display logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */

	
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		log.debug("Do get Method of Error Ctl started");
		// System.out.println("_______________error ctl-_-------->" );
		ServletUtility.forward(getView(), request, response);

		log.debug("Do get Method of Error Ctl End");

		
	}
   	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Do Post Method of Error Ctl started");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do Post Method of Error Ctl End");		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ERROR_VIEW;
	}

}
