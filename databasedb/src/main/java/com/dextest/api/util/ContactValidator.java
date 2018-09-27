package com.dextest.api.util;

import javax.mail.internet.AddressException;


import javax.mail.internet.InternetAddress;

public class ContactValidator {
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
	}
	public static boolean isValidMobileNumber(String mobile) {
		boolean result=false;
		if (mobile.matches("\\d{10}")) {
			result=true;
		}
		return result;
	}
}
