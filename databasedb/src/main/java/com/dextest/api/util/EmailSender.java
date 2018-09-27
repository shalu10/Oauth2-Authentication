package com.dextest.api.util;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {

	public void sendEmail(String to, String subject, String message) {
		String from = "dextestcreation@gmail.com";
		String username= "dextest";
		String password = "Shalini10@";

		Properties properties = new Properties();

		/*properties.put("mail.smtp.starttls.enable", "true");*/
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "587");		
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.fallback","false");
		
		Session session = Session.getDefaultInstance(properties,null );
			session.setDebug(true);

		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(message,"text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com",username,password);

			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			System.out.println("Message Sent Successfully");
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
