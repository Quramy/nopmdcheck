package org.jenkinsci.plugins.nopmdcheck.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

	public static String getSHA(String message) {
		String res = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			
			byte[] b = message.getBytes();
			md.update(b);
			byte [] r = md.digest();
			StringBuffer sb = new StringBuffer();
			for(byte bb:r){
				sb.append(String.format("%02x", bb));
			}
			res = sb.toString();
			return res;

		} catch (NoSuchAlgorithmException e) {
			return res;
		}
	}
}
