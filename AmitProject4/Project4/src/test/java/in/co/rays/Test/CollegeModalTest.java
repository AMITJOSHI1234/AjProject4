package in.co.rays.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.CollegeBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.CollegeModal;

/**
 * College Model Test classes
 *
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
public class CollegeModalTest {
	 /**
     * Model object to test
     */
	static CollegeModal modal = new CollegeModal();
	
	 /**
     * Main method to call test methods.
     *
     * @param args
	 * @throws DublicateRecordException 
	 * @throws ApplicationException 
     * @throws DuplicateRecordException
     */
	
	public static void main(String[] args) throws DublicateRecordException, ApplicationException {
		//testAdd();
		//testDelete();
		 // testUpdate();
		//testFindByName();
		//testFindByPK();
		//testSearch();
		//testList();
	}

	public static void testAdd() throws DublicateRecordException {
		try {
			CollegeBean bean = new CollegeBean();
			bean.setName("apj ABDUL kalam");
			bean.setAddress("National highway");
			bean.setState("M.P");
			bean.setCity("Indore");
			bean.setPhoneNo("07314846353");
	        bean.setCreatedBy("Admin");
	        bean.setModifiedBy("Admin");
	        bean.setCreateDateTime(new Timestamp(new Date().getTime() ));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			System.out.println(bean.getName());
			long pk = modal.add(bean);
			if(pk!=1) {
				System.out.println("Failed!!!");
			}else {
				System.out.println("Pass!!");
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
     * Tests delete a College
     */
	
	public static void testDelete() {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 2L;
			bean.setId(pk);
			modal.delete(bean);
			System.out.println("Test delete successfully!!!!");
			CollegeBean deleteBean = modal.findByPK(pk);
			if(deleteBean!=null) {
				System.out.println("Test delete fail!!!!");
			}else {
				System.out.println("Test delete pass!!!!");
			}
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	/**
     * Tests update a College
	 * @throws DublicateRecordException 
     */
	
	public static void testUpdate() throws DublicateRecordException {
		try {
		CollegeBean bean = modal.findByPK(1L);
		bean.setName("SIRT");
		bean.setAddress("Indore Rau Road");
		modal.update(bean);
		System.out.println("Test update successfull");
		CollegeBean updateBean = modal.findByPK(1L);
		if(!"SIRT".equals(updateBean.getName())) {
			System.out.println("Test Update fail!!!");
		}else {
			System.out.println("Test update pass!!!");
		}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}catch(DublicateRecordException e) {
			e.printStackTrace();
		}
	}
	  /**
     * Tests find a College by Name.
     */
	
	public static void testFindByName() {
			try {
				CollegeBean bean = new CollegeBean();
				bean = modal.findByName("SIRT");
				if(bean==null) {
					System.out.println("Test findbyname fail!!!!");
				}
				
				System.out.println(bean.getId());
				 System.out.println(bean.getName());
		            System.out.println(bean.getAddress());
		            System.out.println(bean.getState());
		            System.out.println(bean.getCity());
		            System.out.println(bean.getPhoneNo());
		            System.out.println(bean.getCreatedBy());
		            System.out.println(bean.getCreateDateTime());
		            System.out.println(bean.getModifiedBy());
		            System.out.println(bean.getModifiedDatetime());		
			}catch(ApplicationException e) {
				e.printStackTrace();
			}
	}
	
	  /**
     * Tests find a College by PK.
	 * @throws ApplicationException 
     */
	public static void testFindByPK() throws ApplicationException {
		try {
		CollegeBean bean = new CollegeBean();
		bean = modal.findByPK(2L);
		if(bean==null) {
			System.out.println("Test findbypk fail!!!");
		}
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
        System.out.println(bean.getAddress());
        System.out.println(bean.getState());
        System.out.println(bean.getCity());
        System.out.println(bean.getPhoneNo());
        System.out.println(bean.getCreatedBy());
        System.out.println(bean.getCreateDateTime());
        System.out.println(bean.getModifiedBy());
        System.out.println(bean.getModifiedDatetime());
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	/**
     * Tests search a College by Name
     */
	public static void testSearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("SIRT");
			list = modal.search(bean,1,1);
			if(list.size()<0) {
				System.out.println("Test search fail!!!");
			}
			
			Iterator it = list.iterator();
			while(it.hasNext()) {
				bean = (CollegeBean)it.next();
				 System.out.println(bean.getId());
	                System.out.println(bean.getName());
	                System.out.println(bean.getAddress());
	                System.out.println(bean.getState());
	                System.out.println(bean.getCity());
	                System.out.println(bean.getPhoneNo());
	                System.out.println(bean.getCreatedBy());
	                System.out.println(bean.getCreateDateTime());
	                System.out.println(bean.getModifiedBy());
	                System.out.println(bean.getModifiedDatetime());
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}	
	}
	  /**
     * Tests get List a College.
     */
	public static void testList() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			list = modal.list(1,10);
			if(list.size()<0) {
				System.out.println("List Test Fail!!!!");
			}
			
			Iterator it = list.iterator();
			while(it.hasNext()) {
				bean = (CollegeBean)it.next();
				  System.out.println(bean.getId());
	                System.out.println(bean.getName());
	                System.out.println(bean.getAddress());
	                System.out.println(bean.getState());
	                System.out.println(bean.getCity());
	                System.out.println(bean.getPhoneNo());
	                System.out.println(bean.getCreatedBy());
	                System.out.println(bean.getCreateDateTime());
	                System.out.println(bean.getModifiedBy());
	                System.out.println(bean.getModifiedDatetime());
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
}
