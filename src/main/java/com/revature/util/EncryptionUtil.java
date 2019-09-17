package com.revature.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
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
    public static String encrypt(String cleartext) {
    	
        try {
        	
        	byte[] cleartextBytes = cleartext.getBytes("UTF-8");
        	
            setKey(AES_KEY);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = cipher.doFinal(cleartextBytes);
            
            byte[] iv = cipher.getIV();
            byte[] message = new byte[12 + cleartextBytes.length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptedBytes, 0, message, 12, encryptedBytes.length);
            
            return Base64.getEncoder().encodeToString(message);
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
    public static String decrypt(String encryptedText) {
    	
    	if(encryptedText == null) return null;
    	
        try {
        	
        	byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText.getBytes("UTF-8"));
        	
            setKey(AES_KEY);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec params = new GCMParameterSpec(128, encryptedBytes, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
            
            return new String(cipher.doFinal(encryptedBytes, 12, encryptedBytes.length - 12), "UTF-8");
        }
        
        catch (Exception e) {
        	
        	LoggerUtil.log.error(e.getStackTrace());
        }
        
        return null;
    }
}
