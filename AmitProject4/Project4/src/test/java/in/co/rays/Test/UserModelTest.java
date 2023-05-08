package in.co.rays.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.UserBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.UserModel;
import com.raystec.project4.util.DataUtility;
import com.raystec.project4.util.PropertyReader;
import com.raystec.project4.util.ServletUtility;


/**
 * User Model Test classes
 *
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class UserModelTest {
	/**
     * Model object to test
     */
	public static UserModel model = new UserModel();
	
	/**
     * Main method to call test methods.
     *
     * @param args
	 * @throws ApplicationException 
     * @throws ParseException
	 * @throws DublicateRecordException 
     * @throws DuplicateRecordException
     */
    public static void main(String[] args) throws ApplicationException, ParseException, DublicateRecordException {
		//testAdd();
    	//testDelete();
    	//testUpdate();
    	testSearch();
    	//testList();
    	//testAuthentication();
    	//testGetRoles();
    	//testFindByLogin();
    	//testFindByPK();
    	//testnextList();
    	//testRandom();
	}
    
	public static void testRandom() {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		int index = ((pageNo - 1) * pageSize) + 1;
		
		System.out.println("PageNo:"+pageNo);
		System.out.println("PageSize:"+pageSize);
		System.out.println("index:"+index);
		
	}

	public static void testnextList() {
		List nextList = null;
		int pageNo =1;
		int pageSize=10;
		System.out.println("Page size is:"+pageSize);
		
		UserBean bean = new UserBean();
		/*
		 * bean.setFirstName("Amit"); bean.setRoleid(2);
		 * bean.setLogin("amit@gmail.com");
		 */
		System.out.println(bean.getFirstName());
		
		UserModel model = new UserModel();
		try {
			nextList = model.search(bean, pageNo+1, pageSize);
			System.out.println(nextList.size());
			System.out.println(nextList);
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}

	/**
     * Tests add a User
     *
     * @throws ParseException
     * @throws DublicateRecordException 
     * @throws DuplicateRecordException
     */
	public static void testAdd() throws ApplicationException, ParseException, DublicateRecordException {
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-yy");
		
		 bean.setFirstName("amit");
         bean.setLastName("joshi");
         bean.setLogin("amit@gmail.com");
         bean.setPassword("Amit@1234");
         bean.setDob(sdf.parse("31-12-1996"));
         bean.setRoleid(1L);
         bean.setUnSuccessfulLogin(1);
         bean.setGender("Male");
         bean.setLastLogin(new Timestamp(new Date().getTime()));
         bean.setLock("Yes");
         bean.setConfirmPassword("Amit@1234");
		long pk=0;
		try {
		 pk = model.add(bean);
		}catch(Exception e) {
			e.printStackTrace();
		}
		UserBean addedBean = model.findByPK(pk);
		System.out.println("Test add succ");
		if(addedBean==null) {
			System.out.println("TestAdd fail");
		}
	}
	
	 /**
     * Tests delete a User
     */
	
	public static void testDelete() {
		try {
			UserBean bean = new UserBean();
			long pk = 3L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete successful!!!!"+bean.getId());
			UserBean deletebean = model.findByPK(pk);
			if(deletebean!=null) {
				System.out.println("Test delete fail!!!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * Tests update a User
     */
  public static void testUpdate() {
	  try {
		  UserBean bean = model.findByPK(3L);
		  bean.setFirstName("Rahul");
		  bean.setLastName("Dev");
		  bean.setLogin("rahul@gmail.com");
		  bean.setPassword("rahul123");
		  
		  model.update(bean);
		  
		  UserBean updatedbean = model.findByPK(3L);
		  if(!"rahul@gmail.com".equals(updatedbean.getLogin())) {
			  System.out.println("Test update fail!!!");
		  }
	  }
             catch(ApplicationException e) {
			  e.printStackTrace();
		  }catch(DublicateRecordException e) {
			  e.printStackTrace();
		  }
	  }
  /**
   * Tests get Search
   */
  public static void testSearch() {

      try {
          UserBean bean = new UserBean();
          List list = new ArrayList();
         // bean.setFirstName("Amit");
          list = model.search(bean, 1, 10);
          if (list.size() < 0) {
              System.out.println("Test Serach fail");
          }
          Iterator it = list.iterator();
          while (it.hasNext()) {
              bean = (UserBean) it.next();
              System.out.println(bean.getId());
              System.out.println(bean.getFirstName());
              System.out.println(bean.getLastName());
              System.out.println(bean.getLogin());
              System.out.println(bean.getPassword());
              System.out.println(bean.getDob());
              System.out.println(bean.getRoleid());
              System.out.println(bean.getUnSuccessfulLogin());
              System.out.println(bean.getGender());
              System.out.println(bean.getLastLogin());
              System.out.println(bean.getLock());
          }

      } catch (ApplicationException e) {
          e.printStackTrace();
      }

  }
  /**
   * Tests get List.
   */
  public static void testList() {
	  
	  try {
		  UserBean bean = new UserBean();
		  List list = new ArrayList();
		  list = model.list(1,10);
		  if(list.size()<0) {
			  System.out.println("Test list fail!!!");
		  }
		  Iterator it = list.iterator();
		  while(it.hasNext()) {
			  bean = (UserBean)it.next();
			  System.out.println(bean.getId());
              System.out.println(bean.getFirstName());
              System.out.println(bean.getLastName());
              System.out.println(bean.getLogin());
              System.out.println(bean.getPassword());
              System.out.println(bean.getDob());
              System.out.println(bean.getRoleid());
              System.out.println(bean.getUnSuccessfulLogin());
              System.out.println(bean.getGender());
              System.out.println(bean.getLastLogin());
              System.out.println(bean.getLock());
              System.out.println(bean.getMobileNo());
              System.out.println(bean.getCreatedBy());
              System.out.println(bean.getModifiedBy());
              System.out.println(bean.getCreateDateTime());
              System.out.println(bean.getModifiedDatetime());
		  }
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
  }
  
  /**
   * Tests authenticate User.
   */
  public static void testAuthentication() {
	  try {
		  UserBean bean = new UserBean();
		  bean.setLogin("rahul@gmail.com");
		  bean.setPassword("rahul123");
		 bean = model.authenticate(bean.getLogin(), bean.getPassword());
		 if(bean!=null) {
			 System.out.println("Successfull login!!!");
		 }else {
			 System.out.println("Invalid email and password!!!");
		 }
	  }catch(Exception e) {
		 e.printStackTrace(); 
	  }
  }
  /**
   * Tests get Roles.
 * @throws ApplicationException 
   */
 public static void testGetRoles() throws ApplicationException {
	 try {
	 UserBean bean = new UserBean();
	 List list = new ArrayList();
	 bean.setRoleid(2L);
	 list = model.getRoles(bean);
	 if(list.size()<0) {
		 System.out.println("Test get Role fail!!!");
	 }
	 Iterator it = list.iterator();
	 while(it.hasNext()) {
		 bean = (UserBean)it.next();
		 System.out.println(bean.getId());
         System.out.println(bean.getFirstName());
         System.out.println(bean.getLastName());
         System.out.println(bean.getLogin());
         System.out.println(bean.getPassword());
         System.out.println(bean.getDob());
         System.out.println(bean.getRoleid());
         System.out.println(bean.getUnSuccessfulLogin());
         System.out.println(bean.getGender());
         System.out.println(bean.getLastLogin());
         System.out.println(bean.getLock());
	 }
 }catch(Exception e) {
	 e.printStackTrace();
 }
  }
 
 /**
  * Tests find a User by Login.
  */
 public static void testFindByLogin() {
     try {
         UserBean bean = new UserBean();
         bean = model.findByLogin("rahul@gmail.com");
         if (bean == null) {
             System.out.println("Test Find By PK fail");
         }
         System.out.println(bean.getId());
         System.out.println(bean.getFirstName());
         System.out.println(bean.getLastName());
         System.out.println(bean.getLogin());
         System.out.println(bean.getPassword());
         System.out.println(bean.getDob());
         System.out.println(bean.getRoleid());
         System.out.println(bean.getUnSuccessfulLogin());
         System.out.println(bean.getGender());
         System.out.println(bean.getLastLogin());
         System.out.println(bean.getLock());
     } catch (ApplicationException e) {
         e.printStackTrace();
     }
 }
 /**
  * Tests find a User by PK.
  */
 public static void testFindByPK() {
     try {
         UserBean bean = new UserBean();
         long pk = 1L;
         bean = model.findByPK(pk);
         if (bean == null) {
             System.out.println("Test Find By PK fail");
         }
         System.out.println(bean.getId());
         System.out.println(bean.getFirstName());
         System.out.println(bean.getLastName());
         System.out.println(bean.getLogin());
         System.out.println(bean.getPassword());
         System.out.println(bean.getDob());
         System.out.println(bean.getRoleid());
         System.out.println(bean.getUnSuccessfulLogin());
         System.out.println(bean.getGender());
         System.out.println(bean.getLastLogin());
         System.out.println(bean.getLock());
     } catch (ApplicationException e) {
         e.printStackTrace();
     }

 }


}