package com.raystec.project4.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.raystec.project4.bean.DropdownListBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DatabaseException;
import com.raystec.project4.util.DataUtility;
import com.raystec.project4.util.JDBCDataSource;

public abstract class BaseModel implements Serializable,DropdownListBean,Comparable<BaseModel> {
 private static Logger log = Logger.getLogger(BaseModel.class);
 
 /**
  * Non Business primary key
  */
 protected long id;
 
 /**
  * User name that creates this record.
  */
 protected String createdBy;
 
 /**
  * User name that modifies this record.
  */
 protected String modifiedBy;
 
 /**
  * Created timestamp of record
  */
 protected Timestamp createdDateTime;
 
 /**
  * Modified timestamp of record
  */
 protected Timestamp modifiedDatetime;

 /**
  * accessor methods
  */

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

public String getModifiedBy() {
	return modifiedBy;
}

public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}

public Timestamp getCreatedDateTime() {
	return createdDateTime;
}

public void setCreatedDateTime(Timestamp createdDateTime) {
	this.createdDateTime = createdDateTime;
}

public Timestamp getModifiedDatetime() {
	return modifiedDatetime;
}

public void setModifiedDatetime(Timestamp modifiedDatetime) {
	this.modifiedDatetime = modifiedDatetime;
}

/**
 * Compares IDs ( Primary Key). If keys are equals then objects are equals.
 *
 */
public int compareTo(BaseModel next) {
	return (int)(id-next.getId());
}

/**
 * created next PK of record
 *
 * @throws DatabaseException
 */

public long nextPK() throws DatabaseException {
	log.debug("Model nextPK started!!!");
	Connection conn = null;
	long pk=0;
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM " + getTableName());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			pk = rs.getInt(1);
		}
		rs.close();
	}catch(Exception e) {
		log.error("DataBase Exception:"+e);
		throw new DatabaseException("Exception:Exception in getting PK");
	}finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model nextPK End");
    return pk + 1;
}

/**
 * Gets table name of Model
 *
 * @return
 */
public abstract String getTableName();

/**
 * Updates created by info
 *
 * @throws ApplicationException
 *
 * @throws Exception
 */
public void updateCreatedInfo() throws Exception {
	log.debug("Model update started..."+createdBy);
	
	Connection conn = null;
	
	String sql = "UPDATE" + getTableName() +"SET CREATED_BY=?, CREATED_DATETIME=? WHERE ID=?";
	log.debug(sql);
	
	try {
		conn = JDBCDataSource.getConnection();//begin transiction
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, createdBy);
		pstmt.setTimestamp(2, DataUtility.getCurrentTimestamp());
		pstmt.setLong(3,id);
		
		int i = pstmt.executeUpdate();
		conn.commit();//End transaction
		pstmt.close();
	}catch(SQLException e) {
		log.error("Database Exception:"+e);
		JDBCDataSource.trnRollback(conn);
		throw new ApplicationException(e.toString());
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model update end!!!!");
}
}
