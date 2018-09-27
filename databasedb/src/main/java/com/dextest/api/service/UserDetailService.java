package com.dextest.api.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dextest.api.repository.UserRepository;
import com.dextest.api.util.ContactValidator;




@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(ContactValidator.isValidEmailAddress(username))
			return userRepository.findByEmail(username);
		else if(ContactValidator.isValidMobileNumber(username))
			return userRepository.findByMobile(username);
		else
			return userRepository.findOneByUsername(username);
	}
}