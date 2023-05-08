package com.raystec.project4.model;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.project4.bean.UserBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DatabaseException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.exception.RecordNotFoundException;
import com.raystec.project4.util.EmailBuilder;
import com.raystec.project4.util.EmailMessage;
import com.raystec.project4.util.EmailUtility;
import com.raystec.project4.util.JDBCDataSource;


/**
 * JDBC Implementation of UserModel
 *
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class UserModel {
public static Logger log = Logger.getLogger(UserModel.class);

/**
* create non bussiness primary key
* @return integer
* @throws DatabaseException
*/

public int nextPK() throws DatabaseException {
	log.debug("Model nextPK started");
	
	String sql = "SELECT MAX(ID) FROM ST_USER";
	Connection conn = null;
	
	int pk=0;
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstms = conn.prepareStatement(sql);
		ResultSet rs = pstms.executeQuery();
		while(rs.next()) {
			pk = rs.getInt(1);
		}
		rs.close();
	}catch(Exception e) {
		log.error("Database Exception:"+e);
		throw new DatabaseException("Exception:Exception in getting pk");
	}finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model nextPK end");
	return pk + 1;
}

/**
* add record in database
* @param bean
* @throws ApplicationException
 * @throws DublicateRecordException 
*  @throws DuplicateRecordException
*/
public long add(UserBean bean) throws ApplicationException, DublicateRecordException {
	log.debug("Model add Started");
    Connection conn = null;
    int pk = 0;

    UserBean existbean = findByLogin(bean.getLogin());

    if (existbean != null) {
        throw new DublicateRecordException("Login Id already exists");
    }

    try {
        conn = JDBCDataSource.getConnection();
        pk = nextPK();
        // Get auto-generated next primary key
        System.out.println(pk + " in ModelJDBC");
        conn.setAutoCommit(false); // Begin transaction
        PreparedStatement pstmt = conn
                .prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, pk);
        pstmt.setString(2, bean.getFirstName());
        pstmt.setString(3, bean.getLastName());
        pstmt.setString(4, bean.getLogin());
        pstmt.setString(5, bean.getPassword());
        pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
        pstmt.setString(7, bean.getMobileNo());
        pstmt.setLong(8, bean.getRoleid());
        pstmt.setInt(9, bean.getUnSuccessfulLogin());
        pstmt.setString(10, bean.getGender());
        pstmt.setTimestamp(11, bean.getLastLogin());
        pstmt.setString(12, bean.getLock());
        pstmt.setString(13, bean.getRegisteredIP());
        pstmt.setString(14, bean.getLastLoginIP());
        pstmt.setString(15, bean.getCreatedBy());
        pstmt.setString(16, bean.getModifiedBy());
        pstmt.setTimestamp(17, bean.getCreateDateTime());
        pstmt.setTimestamp(18, bean.getModifiedDatetime());
        pstmt.executeUpdate();
        conn.commit(); // End transaction
        pstmt.close();
    } catch (Exception e) {
        log.error("Database Exception..", e);
        try {
            conn.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(
                    "Exception : add rollback exception " + ex.getMessage());
        }
        throw new ApplicationException("Exception : Exception in add User");
    } finally {
        JDBCDataSource.closeConnection(conn);
    }
    log.debug("Model add End");
    return pk;	
}

/**
 * Find User by Login
 *
 * @param login
 *            : get parameter
 * @return bean
 * @throws ApplicationException 
 * @throws DatabaseException
 */

public UserBean findByLogin(String login) throws ApplicationException {
	log.debug("Model findByLogin start!!!");
	StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
	UserBean bean = null;
	Connection conn = null;
	System.out.println("sql:"+sql);
	
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, login);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getLong(1));
            bean.setFirstName(rs.getString(2));
            bean.setLastName(rs.getString(3));
            bean.setLogin(rs.getString(4));
            bean.setPassword(rs.getString(5));
            bean.setDob(rs.getDate(6));
            bean.setMobileNo(rs.getString(7));
            bean.setRoleid(rs.getLong(8));
            bean.setUnSuccessfulLogin(rs.getInt(9));
            bean.setGender(rs.getString(10));
            bean.setLastLogin(rs.getTimestamp(11));
            bean.setLock(rs.getString(12));
            bean.setRegisteredIP(rs.getString(13));
            bean.setLastLoginIP(rs.getString(14));
            bean.setCreatedBy(rs.getString(15));
            bean.setModifiedBy(rs.getString(16));
            bean.setCreateDateTime(rs.getTimestamp(17));
            bean.setModifiedDatetime(rs.getTimestamp(18));
		}
		rs.close();
	}catch(Exception e) {
		e.printStackTrace();
		log.error("Database Exception:"+e);
		throw new ApplicationException("Exception:Exception in getting User by Login");
	}finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findByLogin End");
	return bean;
}
/**
 * Find User by PK
 *
 * @param pk
 *            : get parameter
 * @return bean
 * @throws ApplicationException 
 * @throws DatabaseException
 */
 public UserBean findByPK(long pk) throws ApplicationException {
	 log.debug("Model find by pk started!!!");
	 StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
	 UserBean bean = null;
	 Connection conn = null;
	 
	 try {
		 conn = JDBCDataSource.getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		 pstmt.setLong(1, pk);
		 ResultSet rs = pstmt.executeQuery();
		 while(rs.next()) {
			 bean = new UserBean();
			 bean.setId(rs.getLong(1));
			  bean.setFirstName(rs.getString(2));
              bean.setLastName(rs.getString(3));
              bean.setLogin(rs.getString(4));
              bean.setPassword(rs.getString(5));
              bean.setDob(rs.getDate(6));
              bean.setMobileNo(rs.getString(7));
              bean.setRoleid(rs.getLong(8));
              bean.setUnSuccessfulLogin(rs.getInt(9));
              bean.setGender(rs.getString(10));
              bean.setLastLogin(rs.getTimestamp(11));
              bean.setLock(rs.getString(12));
              bean.setRegisteredIP(rs.getString(13));
              bean.setLastLoginIP(rs.getString(14));
              bean.setCreatedBy(rs.getString(15));
              bean.setModifiedBy(rs.getString(16));
              bean.setCreateDateTime(rs.getTimestamp(17));
              bean.setModifiedDatetime(rs.getTimestamp(18));
              
		 }
		 rs.close();
	 }catch(Exception e) {
		 e.printStackTrace();
		 log.error("Database Exception.."+e);
		 throw new ApplicationException("Exception in getting user by pk");
	 }finally {
		 JDBCDataSource.closeConnection(conn);
	 }
	 log.debug("Model findbypk end");
	 return bean;
 }
 
 /**
  * Delete a User
  *
  * @param bean
 * @throws ApplicationException 
  * @throws DatabaseException
  */
 public void delete(UserBean bean) throws ApplicationException {
	 log.debug("Model delete start!!!");
	 Connection conn = null;
	 try {
		 conn = JDBCDataSource.getConnection();
		 conn.setAutoCommit(false);//begin transation
		 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
		 pstmt.setLong(1,bean.getId());
		 int i = pstmt.executeUpdate();
		 conn.commit();//End transition
		 pstmt.close();
	 }catch(Exception e) {
		 log.error("Database Exception:"+e);
		 try {
			 conn.rollback();
		 }catch(Exception ex) {
			 throw new ApplicationException("Exception in rollback:"+ex.getMessage());
		 }
		 throw new ApplicationException("Exception in delete user");
	 }finally {
		 JDBCDataSource.closeConnection(conn);
	 }
	 log.debug("Model delete Exception");
 }
 
 /**
  * Update a user
  *
  * @param bean
 * @throws ApplicationException 
 * @throws DublicateRecordException 
  * @throws DatabaseException
  */
 
 public void update(UserBean bean) throws ApplicationException, DublicateRecordException {
	 log.debug("Model update start!!!");
	 Connection conn = null;
	 
	 UserBean beanExit = findByLogin(bean.getLogin());
	//check if updated login already exits
		 if(beanExit!=null && !(bean.getId() == beanExit.getId())) {
			 throw new DublicateRecordException("Login id already exits");
		 }
		 
		 try {
			 conn = JDBCDataSource.getConnection();
			 conn.setAutoCommit(false);//begin transaction
			 PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?"); 
			 pstmt.setString(1, bean.getFirstName());
	            pstmt.setString(2, bean.getLastName());
	            pstmt.setString(3, bean.getLogin());
	            pstmt.setString(4, bean.getPassword());
	            pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
	            pstmt.setString(6, bean.getMobileNo());
	            pstmt.setLong(7, bean.getRoleid());
	            pstmt.setInt(8, bean.getUnSuccessfulLogin());
	            pstmt.setString(9, bean.getGender());
	            pstmt.setTimestamp(10, bean.getLastLogin());
	            pstmt.setString(11, bean.getLock());
	            pstmt.setString(12, bean.getRegisteredIP());
	            pstmt.setString(13, bean.getLastLoginIP());
	            pstmt.setString(14, bean.getCreatedBy());
	            pstmt.setString(15, bean.getModifiedBy());
	            pstmt.setTimestamp(16, bean.getCreateDateTime());
	            pstmt.setTimestamp(17, bean.getModifiedDatetime());
	            pstmt.setLong(18, bean.getId());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
		 }catch(Exception e) {
			 e.printStackTrace();
			 log.error("DatabaseException:"+e);
			 try {
				 conn.rollback();
			 }catch(Exception ex) {
				throw new ApplicationException("Exception:Delete rollback exception"+ex.getMessage());
			 }
			 throw new ApplicationException("Exception in updating User");
		 }finally {
			 JDBCDataSource.closeConnection(conn);
		 }
		 log.debug("Model update end");
	 }
 
 /**
  * Search User
  *
  * @param bean
  *            : Search Parameters
  * @throws DatabaseException
  */

 public List search(UserBean bean) throws ApplicationException {
     return search(bean, 0, 0);
 }

 /**
  * Search User with pagination
  *
  * @return list : List of Users
  * @param bean
  *            : Search Parameters
  * @param pageNo
  *            : Current Page No.
  * @param pageSize
  *            : Size of Page
  *
  * @throws DatabaseException
  */
 public List search(UserBean bean, int pageNo, int pageSize)
         throws ApplicationException {
     log.debug("Model search Started");
     StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

     if (bean != null) {
         if (bean.getId() > 0) {
             sql.append(" AND id = " + bean.getId());
         }
         if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
             sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
         }
         if (bean.getLastName() != null && bean.getLastName().length() > 0) {
             sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
         }
         if (bean.getLogin() != null && bean.getLogin().length() > 0) {
             sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
         }
         if (bean.getPassword() != null && bean.getPassword().length() > 0) {
             sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
         }
         if (bean.getDob() != null && bean.getDob().getDate() > 0) {
             sql.append(" AND DOB = " + bean.getGender());
         }
         if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
             sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
         }
         if (bean.getRoleid() > 0) {
             sql.append(" AND ROLE_ID = " + bean.getRoleid());
         }
       
			/*
			 * if (bean.getUnSuccessfulLogin() > 0) {
			 * sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfulLogin()); }
			 */
         if (bean.getGender() != null && bean.getGender().length() > 0) {
             sql.append(" AND GENDER like '" + bean.getGender() + "%'");
         }
			/*
			 * if (bean.getLastLogin() != null && bean.getLastLogin().getTime() > 0) {
			 * sql.append(" AND LAST_LOGIN = " + bean.getLastLogin()); } if
			 * (bean.getRegisteredIP() != null && bean.getRegisteredIP().length() > 0) {
			 * sql.append(" AND REGISTERED_IP like '" + bean.getRegisteredIP() + "%'"); } if
			 * (bean.getLastLoginIP() != null && bean.getLastLoginIP().length() > 0) {
			 * sql.append(" AND LAST_LOGIN_IP like '" + bean.getLastLoginIP() + "%'"); }
			 * 
			 */     }

     // if page size is greater than zero then apply pagination
     if (pageSize > 0) {
         // Calculate start record index
         pageNo = (pageNo - 1) * pageSize;

         sql.append(" Limit " + pageNo + ", " + pageSize);
         // sql.append(" limit " + pageNo + "," + pageSize);
     }

     System.out.println(sql);
     ArrayList list = new ArrayList();
     Connection conn = null;
     try {
         conn = JDBCDataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
             bean = new UserBean();
             bean.setId(rs.getLong(1));
             bean.setFirstName(rs.getString(2));
             bean.setLastName(rs.getString(3));
             bean.setLogin(rs.getString(4));
             bean.setPassword(rs.getString(5));
             bean.setDob(rs.getDate(6));
             bean.setMobileNo(rs.getString(7));
             bean.setRoleid(rs.getLong(8));
             bean.setUnSuccessfulLogin(rs.getInt(9));
             bean.setGender(rs.getString(10));
             bean.setLastLogin(rs.getTimestamp(11));
             bean.setLock(rs.getString(12));
             bean.setRegisteredIP(rs.getString(13));
             bean.setLastLoginIP(rs.getString(14));
             bean.setCreatedBy(rs.getString(15));
             bean.setModifiedBy(rs.getString(16));
             bean.setCreateDateTime(rs.getTimestamp(17));
             bean.setModifiedDatetime(rs.getTimestamp(18));

             list.add(bean);
         }
         rs.close();
     } catch (Exception e) {
         log.error("Database Exception..", e);
         throw new ApplicationException(
                 "Exception : Exception in search user");
     } finally {
         JDBCDataSource.closeConnection(conn);
     }

     log.debug("Model search End");
     return list;
 }
 /**
  * Get List of User
  *
  * @return list : List of User
 * @throws ApplicationException 
  * @throws DatabaseException
  */
   public List list() throws ApplicationException {
	   return list(0,0);
   }
   
   /**
    * Get List of User with pagination
    *
    * @return list : List of users
    * @param pageNo
    *            : Current Page No.
    * @param pageSize
    *            : Size of Page
 * @throws ApplicationException 
    * @throws DatabaseException
    */
   
   public List list(int pageNo , int PageSize) throws ApplicationException {
	   log.debug("Model list started!!!");
	   ArrayList list = new ArrayList();
	   StringBuffer sql = new StringBuffer("select * from ST_USER");
       // if page size is greater than zero then apply pagination
         if(PageSize>0) {
        	 //Calculate start record index
        	pageNo = (pageNo-1)*PageSize;
            sql.append(" limit " + pageNo + "," + PageSize);
         }
         Connection conn = null;
         
         try {
        	 conn = JDBCDataSource.getConnection();
        	 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	 ResultSet rs = pstmt.executeQuery();
        	 while(rs.next()) {
        		 UserBean bean = new UserBean();
        		 bean.setId(rs.getLong(1));
                 bean.setFirstName(rs.getString(2));
                 bean.setLastName(rs.getString(3));
                 bean.setLogin(rs.getString(4));
                 bean.setPassword(rs.getString(5));
                 bean.setDob(rs.getDate(6));
                 bean.setMobileNo(rs.getString(7));
                 bean.setRoleid(rs.getLong(8));
                 bean.setUnSuccessfulLogin(rs.getInt(9));
                 bean.setGender(rs.getString(10));
                 bean.setLastLogin(rs.getTimestamp(11));
                 bean.setLock(rs.getString(12));
                 bean.setRegisteredIP(rs.getString(13));
                 bean.setLastLoginIP(rs.getString(14));
                 bean.setCreatedBy(rs.getString(15));
                 bean.setModifiedBy(rs.getString(16));
                 bean.setCreateDateTime(rs.getTimestamp(17));
                 bean.setModifiedDatetime(rs.getTimestamp(18));

                 list.add(bean);
        	 }
        	 rs.close();
         }catch(Exception e) {
        	 log.error("DataBase Exception:"+e);
        	 throw new ApplicationException("Exception:Exception in getting list of user");
         }finally {
        	 JDBCDataSource.closeConnection(conn);
         }
         log.debug("Model list end!!!");
         return list;
   }
   /**
    * @param id
    *            : long id
    * @param old
    *            password : String oldPassword
    * @param new password : String newPassword
 * @throws ApplicationException 
    * @throws DatabaseException
    */
   
   public UserBean authenticate(String login , String password) throws ApplicationException {
	   log.debug("Model authentication start!!!");
	   StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
	   UserBean bean = null;
	   Connection conn = null;
	  
	   try {
		   conn = JDBCDataSource.getConnection();
		   PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		   pstmt.setString(1, login);
		   pstmt.setString(2, password);
		   ResultSet rs = pstmt.executeQuery();
		   while(rs.next()) {
			   bean = new UserBean();
			   bean.setId(rs.getLong(1));
               bean.setFirstName(rs.getString(2));
               bean.setLastName(rs.getString(3));
               bean.setLogin(rs.getString(4));
               bean.setPassword(rs.getString(5));
               bean.setDob(rs.getDate(6));
               bean.setMobileNo(rs.getString(7));
               bean.setRoleid(rs.getLong(8));
               bean.setUnSuccessfulLogin(rs.getInt(9));
               bean.setGender(rs.getString(10));
               bean.setLastLogin(rs.getTimestamp(11));
               bean.setLock(rs.getString(12));
               bean.setRegisteredIP(rs.getString(13));
               bean.setLastLoginIP(rs.getString(14));
               bean.setCreatedBy(rs.getString(15));
               bean.setModifiedBy(rs.getString(16));
               bean.setCreateDateTime(rs.getTimestamp(17));
               bean.setModifiedDatetime(rs.getTimestamp(18));

		   }
	   }catch(Exception e) {
		   log.error("Database Exception..", e);
           throw new ApplicationException("Exception : Exception in get roles");
	   }finally {
		   JDBCDataSource.closeConnection(conn);
	   }
	   log.debug("Model authenticate End");
       return bean;
   }
   
   /**
    * Lock User for certain time duration
    *
    * @return boolean : true if success otherwise false
    * @param login
    *            : User Login
    * @throws ApplicationException
    * @throws RecordNotFoundException
    *             : if user not found
    */
   
   public boolean lock(String login) throws ApplicationException, RecordNotFoundException {
	   log.debug("Service lock started!!!");
	   boolean flag = false;
	   UserBean beanExit = null;
	   try {
		   beanExit = findByLogin(login);
		   if(beanExit!=null) {
			   beanExit.setLock(UserBean.ACTIVE);
			   update(beanExit);
			   flag = true;
		   }else {
			   throw new RecordNotFoundException("LoginId not exits");
		   }
	   }catch(DublicateRecordException e) {
		   log.error("Application Exception:"+e);
		   throw new ApplicationException("Database Exceptiom!!!");
	   }
	   log.debug("Service lock end!!");
	   return flag;
   }
   
   /**
    * Get User Roles
    *
    * @return List : User Role List
    * @param bean
    * @throws ApplicationException
    */
   
   public List getRoles(UserBean bean) throws ApplicationException {
	   log.debug("Model get Roles started!!!");
	   StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE role_Id=?");
	   Connection conn = null;
	   List list = new ArrayList();
	   
	   try {
		   conn = JDBCDataSource.getConnection();
		   PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		   pstmt.setLong(1,bean.getRoleid());
		   ResultSet rs = pstmt.executeQuery();
		   while(rs.next()) {
			   bean = new UserBean();
			   bean.setId(rs.getLong(1));
               bean.setFirstName(rs.getString(2));
               bean.setLastName(rs.getString(3));
               bean.setLogin(rs.getString(4));
               bean.setPassword(rs.getString(5));
               bean.setDob(rs.getDate(6));
               bean.setMobileNo(rs.getString(7));
               bean.setRoleid(rs.getLong(8));
               bean.setUnSuccessfulLogin(rs.getInt(9));
               bean.setGender(rs.getString(10));
               bean.setLastLogin(rs.getTimestamp(11));
               bean.setLock(rs.getString(12));
               bean.setRegisteredIP(rs.getString(13));
               bean.setLastLoginIP(rs.getString(14));
               bean.setCreatedBy(rs.getString(15));
               bean.setModifiedBy(rs.getString(16));
               bean.setCreateDateTime(rs.getTimestamp(17));
               bean.setModifiedDatetime(rs.getTimestamp(18));

               list.add(bean);
		   }
	   }catch(Exception e) {
		  log.error("Database Exception e");
		  throw new ApplicationException("Exception:Exception in getting role");
	   }finally {
		   JDBCDataSource.closeConnection(conn);
	   }
	   log.debug("Model get role end");
	   return list;
   }
   
   /**
	 * change password of an record in database and send mail
	 * @param id
	 * @param old password
	 * @param newPassword
	 * @return boolean
	 * @throws ApplicationException
 * @throws RecordNotFoundException 
	 */
   
   public boolean changePassword(Long id , String oldPassword , String newPassword) throws ApplicationException, RecordNotFoundException {
	   
	   log.debug("changePassword start!!!");
	   boolean flag = false;
	   
	   UserBean beanexit = null;
	   
	   beanexit = findByPK(id);
	   
	   if(beanexit!=null && beanexit.getPassword().equals(oldPassword)) {
		   beanexit.setPassword(newPassword);
		   
		   try {
			   update(beanexit);
		   }catch(DublicateRecordException e) {
			   log.error(e);
			   throw new ApplicationException("LoginId already exits");
		   }
		   flag =true;
	   }else {
		   throw new RecordNotFoundException("Login not exist");
	   }
	   
	   HashMap<String,String> map = new HashMap<String, String>();
	   map.put("login", beanexit.getLogin());
	   map.put("password", beanexit.getPassword());
		map.put("firstname", beanexit.getFirstName());
		map.put("lastName", beanexit.getLastName());
		
		//Uncomment this after understand mail concept
		String message = EmailBuilder.getChangePasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(beanexit.getLogin());
		msg.setSubject("SUNRAYS ORS Password has been changed Successfuly.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
	   log.debug("Model changePassword End");
		return flag;
   }
   /**
	 * register user in database and send mail
	 * @param bean
	 * @return long
	 * @throws ApplicationException
	 */
	
	public long registerUser(UserBean bean) throws ApplicationException, DublicateRecordException {
		log.debug("Model add Started");
		long pk = add(bean);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		//Uncomment this after understand mail concept
		String message = EmailBuilder.getUserRegistrationMessage(map);
		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is Successful for ORS Project Sunilos");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		return pk;
	}
	
	/**
	 * get password of an record from database and send mail
	 * @param login Id
	 * @return boolean
	 * @throws ApplicationException
	 */
	
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserBean userData = findByLogin(login);
		boolean flag = false;

		if (userData == null) {
			throw new RecordNotFoundException("Email Id does not exist !");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName()); 
		map.put("lastName", userData.getLastName());
		//Uncomment this after understand mail concept

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("Sunrays ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		flag = true;
		return flag;
	}


 }
 