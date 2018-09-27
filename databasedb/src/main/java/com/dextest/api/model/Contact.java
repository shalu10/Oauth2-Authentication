package com.dextest.api.model;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Contacts")
public class Contact implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long contactId;
	

	@JsonManagedReference
	@ManyToOne
	private User userId;
	
	private String type;
	
	private String data;

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
