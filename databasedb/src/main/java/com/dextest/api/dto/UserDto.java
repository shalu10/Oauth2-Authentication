package com.dextest.api.dto;

import java.util.List;

public class UserDto {
	public String id;
	public String username;
	public String firstName;
	public String lastName;
	public List<String> email;
	public List<String> mobile;
	public UserDto() {
		super();
		
	}
	
	public UserDto(String id, String username, String firstName, String lastName, List<String> email,
			List<String> mobile) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public List<String> getEmail() {
		return email;
	}
	public void setEmail(List<String> email) {
		this.email = email;
	}
	public List<String> getMobile() {
		return mobile;
	}
	public void setMobile(List<String> mobile) {
		this.mobile = mobile;
	}
	
	

	


	
	
	
}
