package com.dhanya.mini.commonlib.domain;

/** 
 * User domain object that will be persisted to the database
 * 
 * @author Leela
 */
public class UserDomain {
	
	private int id;
	
	private String uuid;
	
	private String firstName;
	
	private String lastName;
	
	private String gender;
	
	private String googleId;
	
	public int getId() {
		return id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
}