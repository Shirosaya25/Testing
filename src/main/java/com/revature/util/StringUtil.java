package com.revature.util;

public class StringUtil {
	
	private final static String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	private final static String USERNAME_REGEX = "^(?=.{6,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private final static String MONEY_REGEX = "[^.0-9]";
	
	public static boolean isValidUsername(String username) { return username.matches(USERNAME_REGEX);}
	public static boolean isValidEmail(String email) { return email.matches(EMAIL_REGEX);}
	
	public static boolean isValidAmount(double num) {
		
		String amount = new Double(num).toString();
		
		if(amount.matches(MONEY_REGEX)) return false;
		
		int idx = amount.indexOf('.');
		
		if(idx == -1) return true;
		
		return(amount.substring(idx).length() <= 3);
		
	}
}