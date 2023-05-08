package com.raystec.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.raystec.project4.util.ServletUtility;
/**
* Welcome functionality Controller. Performs operation for Show Welcome page
* 
*  @author Amit Joshi
*/
/**
 * Servlet implementation class WelcomeCtl
 */
@WebServlet(name ="WelcomeCtl",urlPatterns = { "/WelcomeCtl" })
public class WelcomeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	/** The log. */
	private static Logger log = Logger.getLogger(WelcomeCtl.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("WelcomeCtl Method doGet Started");

		ServletUtility.forward(getView(), request, response);

		log.debug("WelcomeCtl Method doGet Ended");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.WELCOME_VIEW;
	}

}
