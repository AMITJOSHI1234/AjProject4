package com.raystec.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.project4.bean.CollegeBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DatabaseException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.util.JDBCDataSource;

/**
 * JDBC Implementation of CollegeModel
 * 
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class CollegeModal {
private static Logger log = Logger.getLogger(CollegeModal.class);
/*
 * Find next PK of College
 * 
 * @return
 * @throws DatabaseException
 */

 public Integer nextPk() throws DatabaseException{
	log.debug("nextPk started");
	Connection conn = null;
	int pk=0;
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("select max(id) from ST_COLLEGE");
		ResultSet rs = ps.executeQuery();
		
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
	log.debug("Modal nextPk end");
	return pk+1; 
 }
 /**
  * Add a College
  * 
  * @param bean
  * @return 
 * @throws ApplicationException 
 * @throws DublicateRecordException 
  * @throws DatabaseException
  * 
  */
 
 public long add(CollegeBean bean) throws ApplicationException, DublicateRecordException {
	 log.debug("Model add started");
	 Connection conn = null;
	 int i=0;
	 
	 CollegeBean dublicateCollegename = findByName(bean.getName());
	 
	 if(dublicateCollegename!=null) {
		 throw new DublicateRecordException("College name already exits!!!");	 }
	// System.out.println(bean.getName());
	 
	 try {
		 System.out.println(bean.getName());
		 try {
		 conn = JDBCDataSource.getConnection();
		// Get auto-generated next primary key
		// pk = nextPk();
		 }catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
		 conn.setAutoCommit(false);//begin transaction
		 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
		 pstmt.setInt(1, nextPk());
         pstmt.setString(2, bean.getName());
         System.out.println(bean.getName());
         pstmt.setString(3, bean.getAddress());
         pstmt.setString(4, bean.getState());
         pstmt.setString(5, bean.getCity());
         pstmt.setString(6, bean.getPhoneNo());
         pstmt.setString(7, bean.getCreatedBy());
         pstmt.setString(8, bean.getModifiedBy());
         pstmt.setTimestamp(9, bean.getCreateDateTime());
         pstmt.setTimestamp(10, bean.getModifiedDatetime());
          i = pstmt.executeUpdate();
         conn.commit(); // End transaction
         pstmt.close();	 
	 }catch(Exception e) {
		 log.error("Database Exception:"+e);
		 try {
			 conn.rollback();
		 }catch(Exception ex) {
			 ex.printStackTrace();
			 throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());		 
		 }
		 throw new ApplicationException(
                 "Exception : Exception in add College");
	 }finally {
		 JDBCDataSource.closeConnection(conn);
	 }
	 
	 log.debug("Model add End");
     return i;
 }
 
 /**
  * Find User by College
  * 
  * @param login
  *            : get parameter
  * @return bean
 * @throws ApplicationException 
  * @throws DatabaseException
  */
 
 public CollegeBean findByName(String name) throws ApplicationException {
	 log.debug("Model findByName started!!!");
	 StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE NAME=?");
	 Connection conn = null;
	 CollegeBean bean = null;
	 try {
		 conn = JDBCDataSource.getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		 pstmt.setString(1, name);
		 ResultSet rs = pstmt.executeQuery();
		 while(rs.next()) {
			 bean = new CollegeBean();
			 bean.setId(rs.getLong(1));
			 bean.setId(rs.getLong(1));
             bean.setName(rs.getString(2));
             bean.setAddress(rs.getString(3));
             bean.setState(rs.getString(4));
             bean.setCity(rs.getString(5));
             bean.setPhoneNo(rs.getString(6));
             bean.setCreatedBy(rs.getString(7));
             bean.setModifiedBy(rs.getString(8));
             bean.setCreateDateTime(rs.getTimestamp(9));
             bean.setModifiedDatetime(rs.getTimestamp(10));
		 }
	 }catch(Exception e) {
		 log.error("Database Exception..", e);
         throw new ApplicationException(
                 "Exception : Exception in getting College by Name");
	 }finally {
         JDBCDataSource.closeConnection(conn);
     }
     log.debug("Model findByName End");
	return bean;
 }
 
 /**
  * Find User by College
  * 
  * @param pk
  *            : get parameter
  * @return bean
  * @throws ApplicationException
  */
 public CollegeBean findByPK(long pk) throws ApplicationException {
     log.debug("Model findByPK Started");
     StringBuffer sql = new StringBuffer(
             "SELECT * FROM ST_COLLEGE WHERE ID=?");
     CollegeBean bean = null;
     Connection conn = null;
     try {

         conn = JDBCDataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
         pstmt.setLong(1, pk);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
             bean = new CollegeBean();
             bean.setId(rs.getLong(1));
             bean.setName(rs.getString(2));
             bean.setAddress(rs.getString(3));
             bean.setState(rs.getString(4));
             bean.setCity(rs.getString(5));
             bean.setPhoneNo(rs.getString(6));
             bean.setCreatedBy(rs.getString(7));
             bean.setModifiedBy(rs.getString(8));
             bean.setCreateDateTime(rs.getTimestamp(9));
             bean.setModifiedDatetime(rs.getTimestamp(10));

         }
         rs.close();
     } catch (Exception e) {
         log.error("Database Exception..", e);
         throw new ApplicationException(
                 "Exception : Exception in getting College by pk");
     } finally {
         JDBCDataSource.closeConnection(conn);
     }
     log.debug("Model findByPK End");
     return bean;
 }
 
 /**
  * Delete a College
  * 
  * @param bean
 * @throws ApplicationException 
  * @throws DatabaseException
  */
 
 public void delete(CollegeBean bean) throws ApplicationException {
	 log.debug("Delete model start");
	 
	 Connection conn = null;
	 try {
		 conn = JDBCDataSource.getConnection();
		 conn.setAutoCommit(false);//Begin Transition
		 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
		 pstmt.setLong(1, bean.getId());
		 int i = pstmt.executeUpdate();
		 conn.commit();//End transition
		 pstmt.close();
	 }catch(Exception e) {
		 log.error("Database Exception..."+e);
		 try {
			 conn.rollback();
		 }catch(Exception ex) {
			 throw new ApplicationException("Exception:Delete Rollback Exception:"+ex.getMessage());
		 }
		 
		 throw new ApplicationException("Exception:Exception in deleting college:"+e.getMessage());
	 }finally {
		JDBCDataSource.closeConnection(conn);
	}
	 log.debug("Model delete ended");
 }
 
 /**
  * Update a College
  * 
  * @param bean
 * @throws ApplicationException 
 * @throws DublicateRecordException 
  * @throws DatabaseException
  */
 
 public void update(CollegeBean bean) throws ApplicationException, DublicateRecordException {
	 log.debug("Model update start");
	 Connection conn=null;
	 
	 CollegeBean beanExist = findByName(bean.getName());
	 //check if updated college is already exits
	 if(beanExist!=null && beanExist.getId()!=bean.getId()) {
		 throw new DublicateRecordException("College is already exists!!!");
	 }
	 
	 try {
		 conn = JDBCDataSource.getConnection();
		 conn.setAutoCommit(false);//begin transition
		 PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		 pstmt.setString(1, bean.getName());
         pstmt.setString(2, bean.getAddress());
         pstmt.setString(3, bean.getState());
         pstmt.setString(4, bean.getCity());
         pstmt.setString(5, bean.getPhoneNo());
         pstmt.setString(6, bean.getCreatedBy());
         pstmt.setString(7, bean.getModifiedBy());
         pstmt.setTimestamp(8, bean.getCreateDateTime());
         pstmt.setTimestamp(9, bean.getModifiedDatetime());
         pstmt.setLong(10, bean.getId());
         pstmt.executeUpdate();
         conn.commit(); // End transaction
	 }catch(Exception e) {
		 log.error("Datbase Exception:"+e);
		 try {
			 conn.rollback();
		 }catch(Exception ex) {
			 throw new ApplicationException("Exception : Delete rollback exception:"+ex.getMessage());
		 }
		 
		 throw new ApplicationException("Exception in updating college");
	 }finally {
		JDBCDataSource.closeConnection(conn);
	}
	 log.debug("Modal update end");
 }
 
 /**
  * Search College with pagination
  * 
  * @return list : List of Users
  * @param bean
  *            : Search Parameters
  * @param pageNo
  *            : Current Page No.
  * @param pageSize
  *            : Size of Page
 * @throws ApplicationException 
  * 
  * @throws DatabaseException
  */
 
 public List search(CollegeBean bean , int pageNo , int pageSize) throws ApplicationException {
	 log.debug("Model search Started");
	 StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");
	 if (bean != null) {
         if (bean.getId() > 0) {
             sql.append(" AND id = " + bean.getId());
         }
         if (bean.getName() != null && bean.getName().trim().length() > 0) {
             sql.append(" AND NAME like '" + bean.getName() + "%'");
         }
         if (bean.getAddress() != null && bean.getAddress().length() > 0) {
             sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
         }
         if (bean.getState() != null && bean.getState().length() > 0) {
             sql.append(" AND STATE like '" + bean.getState() + "%'");
         }
         if (bean.getCity() != null && bean.getCity().trim().length() > 0) {
             sql.append(" AND CITY like '" + bean.getCity() + "%'");
         }
         if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
             sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
         }

     }

	 
	 
	 //if pagesize is greter than zero apply pagination
	 if(pageSize>0) {
		 //Calculate record index
		 pageNo = (pageNo-1)*pageSize;
		 
		 sql.append(" Limit " + pageNo + ", " + pageSize);
	 }
	 
	 ArrayList list = new ArrayList();
	 
	 Connection conn = null;
	 try {
		 conn = JDBCDataSource.getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		 ResultSet rs = pstmt.executeQuery();
		 while(rs.next()) {
			 bean = new CollegeBean();
			 bean.setId(rs.getLong(1));
			 bean.setName(rs.getString(2));
             bean.setAddress(rs.getString(3));
             bean.setState(rs.getString(4));
             bean.setCity(rs.getString(5));
             bean.setPhoneNo(rs.getString(6));
             bean.setCreatedBy(rs.getString(7));
             bean.setModifiedBy(rs.getString(8));
             bean.setCreateDateTime(rs.getTimestamp(9));
             bean.setModifiedDatetime(rs.getTimestamp(10));
             list.add(bean);
		 }
		 rs.close();
	 }catch(Exception e) {
		 log.error("DatabaseException"+e);
		 throw new ApplicationException("Exception:Exception in search college");
	 }finally {
		JDBCDataSource.closeConnection(conn);
	}
	 log.debug("search model end");
	 return list;
 }
 
 /**
  * Search College
  * 
  * @param bean
  *            : Search Parameters
 * @throws ApplicationException 
  * @throws DatabaseException
  */
 
 public List search(CollegeBean bean) throws ApplicationException {
	 return search(bean,0,0);
 }
 
 /**
  * Get List of College
  * 
  * @return list : List of College
 * @throws ApplicationException 
  * @throws DatabaseException
  */
 
 public List list() throws ApplicationException {
	 return list(0, 0);
 }
 /**
  * Get List of College with pagination
  * 
  * @return list : List of College
  * @param pageNo
  *            : Current Page No.
  * @param pageSize
  *            : Size of Page
 * @throws ApplicationException 
  * @throws DatabaseException
  */
 public List list(int PageNo , int PageSize) throws ApplicationException {
	 log.debug("Model list started");
	 ArrayList list = new ArrayList();
	 StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
	// if page size is greater than zero then apply pagination
	 
	 if(PageSize>0) {
		// Calculate start record index
		 PageNo = (PageNo-1)*PageSize;
		 sql.append(" limit " + PageNo + "," + PageSize);
	 }
	 
	 Connection conn = null;
	 try {
		 conn = JDBCDataSource.getConnection();
		 PreparedStatement psmst = conn.prepareStatement(sql.toString());
		 ResultSet rs = psmst.executeQuery();
		 while(rs.next()) {
			 CollegeBean bean = new CollegeBean();
			 bean.setId(rs.getLong(1));
			 bean.setName(rs.getString(2));
             bean.setAddress(rs.getString(3));
             bean.setState(rs.getString(4));
             bean.setCity(rs.getString(5));
             bean.setPhoneNo(rs.getString(6));
             bean.setCreatedBy(rs.getString(7));
             bean.setModifiedBy(rs.getString(8));
             bean.setCreateDateTime(rs.getTimestamp(9));
             bean.setModifiedDatetime(rs.getTimestamp(10));
             list.add(bean);
		 }
		 rs.close();
	 }catch(Exception e) {
		 log.error("DataBase Exception:"+e);
		 throw new ApplicationException("Exception:Exception in getting list of users");
	 }finally {
		 JDBCDataSource.closeConnection(conn);
	 }
	return list;
	 
	 
 }
 
 
}
