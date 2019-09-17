package com.revature.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class EncryptionUtil {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    
    private final static String AES_KEY = System.getenv("AES_KEY");
    
    private EncryptionUtil() {
    	
    	throw new IllegalStateException("Utility Class.");
    }
 
    /**
     * Creates a schedule of 16 round-keys and sub-bytes table using the key provided
     * @param myKey - AES 256 key
     */
    public static void setKey(String myKey) {
    	
        MessageDigest sha = null;
        
        try {
        	
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");          
        }
        
        catch (NoSuchAlgorithmException e) {
        	
        	LoggerUtil.log.error(e.getStackTrace());
        }
        
        catch (UnsupportedEncodingException e) {
        	
        	LoggerUtil.log.error(e.getStackTrace());
        }
    }
 
    /**
     * Encrypts a string using the AES key provided with the system variables
     * @param strToEncrypt
     * @return AES 256 encrypted string
     */
    public static String encrypt(String strToEncrypt) {
    	
        try {
        	
            setKey(AES_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        
        catch (Exception e) {
        	
        	LoggerUtil.log.error(e.getStackTrace());
        }
        
        return null;
    }
 
    /**
     * Decrypts a string using the AES key provided with the system variables
     * @param strToDecrypt
     * @return decrypted String
     */
    public static String decrypt(String strToDecrypt) {
    	
    	if(strToDecrypt == null) return null;
    	
        try {
        	
            setKey(AES_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt.getBytes("UTF-8"))));
        }
        
        catch (Exception e) {
        	
        	LoggerUtil.log.error(e.getStackTrace());
        }
        
        return null;
    }
}
