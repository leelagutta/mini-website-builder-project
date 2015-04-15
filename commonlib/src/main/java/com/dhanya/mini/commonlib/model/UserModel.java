package com.dhanya.mini.commonlib.model;

/**
 * User model object that is returned to the client
 * 
 * @author Leela
 */
public class UserModel {
	
	private String uuid;
	
	private String firstName;
	
	private String lastName;
	
	private String gender;
	
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
}