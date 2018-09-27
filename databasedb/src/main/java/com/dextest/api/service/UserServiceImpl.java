package com.dextest.api.service;

import java.security.Principal;

import java.time.LocalDateTime;
import java.util.ArrayList;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dextest.api.dto.RegisterDto;
import com.dextest.api.dto.UserDto;
import com.dextest.api.model.Contact;
import com.dextest.api.model.User;
import com.dextest.api.repository.ContactRepository;
import com.dextest.api.repository.UserRepository;
import com.dextest.api.util.ContactValidator;
import com.dextest.api.util.EmailSender;



@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private EmailSender emailsender = new EmailSender();


	
	@Override
	public UserDto findByEmail(String email) {
		User user=userRepository.findByEmail(email);
		if(user!=null) {
			List<String> emails=new ArrayList<>();
			List<String> mobiles=new ArrayList<>();
			for(Contact c : user.getContacts()) {
				if(c.getType().equals("email"))
					emails.add(c.getData());
				else if(c.getType().equals("mobile"))
					mobiles.add(c.getData());
			}
			UserDto dto=new UserDto(user.getUserId().toString(), user.getUsername(), user.getFirstName(),user.getLastName(), emails, mobiles);
			return dto;			
		}else {
			return null;
		}	
	}
	
	@Override
	public UserDto findByMobile(String mobile) {		
		User user=userRepository.findByMobile(mobile);
		if(user!=null) {
			List<String> emails=new ArrayList<>();
			List<String> mobiles=new ArrayList<>();
			for(Contact c : user.getContacts()) {
				if(c.getType().equals("email"))
					emails.add(c.getData());
				else if(c.getType().equals("mobile"))
					mobiles.add(c.getData());
			}
			UserDto dto=new UserDto(user.getUserId().toString(), user.getUsername(),user.getFirstName(),user.getLastName(), emails, mobiles);
			return dto;			
		}else {
			return null;
		}	
	}
	
	@Override
	public UserDto findByUsername(String username) {
		User user=userRepository.findOneByUsername(username);
		if(user!=null) {
			List<String> emails=new ArrayList<>();
			List<String> mobiles=new ArrayList<>();
			for(Contact c : user.getContacts()) {
				if(c.getType().equals("email"))
					emails.add(c.getData());
				else if(c.getType().equals("mobile"))
					mobiles.add(c.getData());
			}
			UserDto dto=new UserDto(user.getUserId().toString(),user.getUsername(),user.getFirstName(),user.getLastName(), emails, mobiles);
			return dto;			
		}else {
			return null;
		}	
	}


	@Override
	public User validate(String contact, String password) {
		if(ContactValidator.isValidEmailAddress(contact) || ContactValidator.isValidMobileNumber(contact)) {
			
		}
	
		return null;
	}

	@Override
	public User findById(String uuid) {		
		return userRepository.findById(uuid).get();
	}

	@Override
	public User update(User user) {
		userRepository.save(user);
		return user;
	}

	@Override
	public void registerUser(RegisterDto dto) {
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		User user=new User();		
		user.setUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());

		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setEnabled(true);			
		Contact contact=new Contact();
		if(dto.getContact()!=null) {
			if(ContactValidator.isValidEmailAddress(dto.getContact())) {
				contact.setType("email");
				contact.setData(dto.getContact());	
				contact.setUserId(user);	
				String message = "Hello " + user.getUsername() + " you're successfully registered with us, Thanks !";
				try {			
					emailsender.sendEmail(dto.getContact(), "Registration Successfull", message);
				} catch (Exception e) {

				}
			}else if(ContactValidator.isValidMobileNumber(dto.getContact())) {
				contact.setType("mobile");
				contact.setData(dto.getContact());
				contact.setUserId(user);				
			}
			if(user.getUsername()==null || user.getUsername()=="") {
				user.setUsername(dto.getContact());
			}			
			
		}
		user.getContacts().add(contact);
		userRepository.save(user);		
		
		
	}
	@Override

	@Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
          /*  String principalId = (String) map.get("id");
            User user = userRepository.findByPrincipalId(principalId);
            if (user == null) {
                LOGGER.info("No user found, generating profile for {}", principalId);*/
        	/*BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();*/
                User user = new User();
                /*user.setPassword(passwordEncoder.encode((String) map.get("password")));*/
                user.setCreated(LocalDateTime.now());
                user.setUsername((String) map.get("username"));
                user.setFirstName((String) map.get("given_name"));
                user.setLastName((String) map.get("family_name"));
                user.setAvatar((String) map.get("picture"));
                user.setLastLogin(LocalDateTime.now());
                user.setPrincipalId((String)map.get("id"));
               
        		user.setEnabled(true);	      
                Contact contact=new Contact();
        		if((String)map.get("email")!=null) {
        			if(ContactValidator.isValidEmailAddress((String)map.get("email"))) {
        				contact.setType("email");
        				contact.setData((String)map.get("email"));	
        				contact.setUserId(user);			
        			}else if(ContactValidator.isValidMobileNumber((String)map.get("mobile"))) {
        				contact.setType("mobile");
        				contact.setData((String)map.get("mobile"));
        				contact.setUserId(user);				
        			}
        			if(user.getUsername()==null || user.getUsername()=="") {
        				user.setUsername((String)map.get("name"));
        			}			
        			
        		}
        		user.getContacts().add(contact);
            userRepository.save(user);
            return user;
	
	
        };
    
    
}
}
