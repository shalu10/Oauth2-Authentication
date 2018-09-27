package com.dextest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.dextest.api.model.Contact;




public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	boolean findByData(String data); 
}
