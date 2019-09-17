package com.revature.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {

	public static final Random RANDOM = new SecureRandom();
	
	public RandomUtil() throws IllegalStateException {
		
		throw new IllegalStateException("Utility Class");
	}
}
