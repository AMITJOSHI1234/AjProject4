package com.raystec.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raystec.project4.bean.BaseBean;
import com.raystec.project4.bean.UserBean;
import com.raystec.project4.util.DataUtility;
import com.raystec.project4.util.DataValidator;
import com.raystec.project4.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 *
 * @author  Amit Joshi
 *
 */

public abstract class BaseCtl extends HttpServlet {
	
	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_RESET = "Reset";
	public static final String OP_UPDATE = "Update";

	/**
	 * Success message key constant
	 */
	public static final String MSG_SUCCESS = "success";
	
	/**
	 * Error message key constant
	 */
	public static final String MSG_ERROR = "error";
	
	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}
	
	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
	}
	
	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("poplt");
		return null;
	}
	
	/**
	 * Populates Generic attributes in DTO
	 *
	 * @param dto
	 * @param request
	 * @return
	 */
	protected BaseBean populateDTO(BaseBean dto , HttpServletRequest request) {
		
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;
		
		UserBean userbean = (UserBean) request.getSession().getAttribute("user");
		
		if(userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		}else {
			
			modifiedBy = userbean.getLogin();
			
			// If record is created first time
			if("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		
		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));
		
		if(cdt>0) {
			dto.setCreateDateTime(DataUtility.getTimestamp(cdt));
		}else {
			dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		}
		
		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		
		return dto;
	}
	
	@Override
	protected void service(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Bctl service!!");
		
		// Load the preloaded data required to display at HTML form
		preload(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Basic servi op"+op);
		// Check if operation is not DELETE, VIEW, CANCEL, reset and NULL then
				// perform input data validation
		
		if(DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op) && !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("Bctl 5 validation");
				
			if(!validate(request)) {
				System.out.println("Bctl validate!!!");
				BaseBean bean = (BaseBean)populateBean(request);
				//wapis se inserted data dikhe jo phle in put kiya tha
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}	
		}
		System.out.println("B ctl super service");
		
		//Decide whether to run get method or postmethod.
		super.service(request, response);
	}

	/**
	 * Returns the VIEW page of this Controller
	 *
	 * @return
	 */
	protected abstract String getView();
}
