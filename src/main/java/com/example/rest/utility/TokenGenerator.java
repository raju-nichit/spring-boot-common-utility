package com.example.rest.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class TokenGenerator {

	private static Logger logger = Logger.getLogger(TokenGenerator.class);
	private TokenGenerator() {

	}
	
	/**
	 * This will generate token
	 * 
	 * @param email
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateToken(String email) {
		StringBuilder authKey = new StringBuilder();
		try {
			byte[] bytesOfMessage = (String.valueOf(System.currentTimeMillis()) + email).getBytes();

			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] thedigest = md.digest(bytesOfMessage);

			for (byte b : thedigest) {
				authKey.append(Integer.toHexString((int) (b & 0xff)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return authKey.toString();
	}
}
