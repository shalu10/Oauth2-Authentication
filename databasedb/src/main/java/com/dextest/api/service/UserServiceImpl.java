package com.dextest.api.service;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;


import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dextest.api.dto.RegisterDto;
import com.dextest.api.dto.UserDto;
import com.dextest.api.model.Contact;
import com.dextest.api.model.User;
import com.dextest.api.repository.ContactRepository;
import com.dextest.api.repository.UserRepository;
import com.dextest.api.util.ContactValidator;




@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	


	
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
		/*BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();*/
		User user=new User();		
		user.setUsername(dto.getUsername());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEnabled(true);	
		/*user.setPassword(passwordEncoder.encode(dto.getPassword()));*/
			
		Contact contact=new Contact();
		if(dto.getContact()!=null) {
			if(ContactValidator.isValidEmailAddress(dto.getContact())) {
				contact.setType("email");
				contact.setData(dto.getContact());	
				contact.setUserId(user);	
				contact.setCreatedAt(LocalDateTime.now());
				contact.setUpdatedAt(LocalDateTime.now());
				user.setEnabled(false);	
				contact.setConfirmationToken(UUID.randomUUID().toString());
				final String username = "dextestcreation@gmail.com";
				final String password = "Shalini10@";
		 
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
		                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
		 
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });				
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("noreply@domain.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(dto.getContact()));
					message.setSubject("Registration Confirmation");
					message.setText("To confirm your e-mail address, please click the link below:\n" + "http://localhost:9080" + "/confirm?token="+contact.getConfirmationToken());
		 
					Transport.send(message);
		 					
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}	
			}else if(ContactValidator.isValidMobileNumber(dto.getContact())) {
				contact.setType("mobile");
				contact.setData(dto.getContact());
				contact.setUserId(user);		
				contact.setCreatedAt(LocalDateTime.now());
				contact.setUpdatedAt(LocalDateTime.now());
				user.setEnabled(false);	
				char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
				Random rnd = new Random();
				StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
				for (int i = 0; i < 5; i++)
				    sb.append(chars[rnd.nextInt(chars.length)]);
				contact.setGenerateOtp(sb.toString());
				
				try {
					// Construct data
					String apiKey = "apikey=" + "Yn2BvW3C3fs-qmgn5rnhQKCdsTOjbsxoCAd0Ie4Lxj";
					String message = "&message=" + "To verify your Mobile number, please enter the OTP number given below :\n" + contact.getGenerateOtp();
					String sender = "&sender=" + "TXTLCL";
					/*String otp = "&otp=" + "mt_rand(10000,9999)";*/
					String numbers = "&numbers=" + (dto.getContact());
					
					// Send data
					HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
					String data = apiKey + numbers + message + sender;
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
					conn.getOutputStream().write(data.getBytes("UTF-8"));
					final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					final StringBuffer stringBuffer = new StringBuffer();
					String line;
					while ((line = rd.readLine()) != null) {
						stringBuffer.append(line);
					}
					rd.close();
					 System.out.println(stringBuffer.toString());
					
				} catch (Exception e) {
					System.out.println("Error SMS "+e);
			
				}
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
        	BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
                User user = new User();
                user.setPassword(passwordEncoder.encode((String) map.get("password")));
               
                user.setUsername((String) map.get("username"));
                user.setFirstName((String) map.get("given_name"));
                user.setLastName((String) map.get("family_name"));
                user.setAvatar((String) map.get("picture"));
             
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

	/*@Override
	public User findByConfirmationToken(String confirmationToken) {
		return userRepository.findByConfirmationToken(confirmationToken);
	}*/
}
