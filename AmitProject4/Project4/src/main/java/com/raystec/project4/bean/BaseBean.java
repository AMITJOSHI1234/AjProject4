package com.raystec.project4.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Parent class of all Beans in application. It contains generic attributes.
 * 
 * @author Amit joshi
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */


public abstract class BaseBean implements Serializable,DropdownListBean,Comparable<BaseBean> {
	
	   /**
     * Non Business primary key
     */
	protected long id;
	
	/**
     * Contains USER ID who created this database record
     */
	
	protected String createdBy;
	
	/**
     * Contains USER ID who modified this database record
     */
	protected String modifiedBy;
	
	/**
     * Contains Created Timestamp of database record
     */
	
	protected Timestamp createDateTime;
	
	/**
     * Contains Modified Timestamp of database record
     */
	
	protected Timestamp modifiedDatetime;

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

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}
	
	public int compareTo(BaseBean next) {
		return getValue().compareTo(next.getValue());
		
	}

}
