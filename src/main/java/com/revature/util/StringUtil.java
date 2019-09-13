package com.revature.util;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class StringUtil {
	
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	private static final String USERNAME_REGEX = "^(?=.{6,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private static final String MONEY_REGEX = "[^.0-9]";
	
	private StringUtil() {
		
		throw new IllegalStateException("Utility Class");
	}
	
	public static boolean isValidUsername(String username) { return username.matches(USERNAME_REGEX);}
	public static boolean isValidEmail(String email) { return email.matches(EMAIL_REGEX);}
	public static boolean isValidAmount(double num) {
		
		String amount = Double.toString(num);
		
		if(amount.matches(MONEY_REGEX)) return false;
		
		if (num <= 0) return false;
		
		int idx = amount.indexOf('.');
		
		if(idx == -1) return true;
		
		return(amount.substring(idx).length() <= 3);
		
	}
	
	public static boolean isValidToken(String token) {  
		
		if(token == null) return false;
		String[] s = token.split(".");
		
		return s.length == 3;
	}
	
	public static String getRandomString() {
		
		byte[] array = new byte[8];
		new Random().nextBytes(array);
		
		return new String(array,StandardCharsets.UTF_8);
	}
}