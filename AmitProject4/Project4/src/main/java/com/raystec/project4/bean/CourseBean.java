package com.raystec.project4.bean;
/**
 * Course JavaBean encapsulates Course attributes.
 * 
 * @author Amit joshi
 *
 */

public class CourseBean extends BaseBean {
	private String name ;
	private String description;
	private String duration;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getValue() {
		return name;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

}
