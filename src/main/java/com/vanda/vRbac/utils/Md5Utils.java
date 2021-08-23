package com.vanda.vRbac.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

public class Md5Utils {
	
	/**
	 * md5加密
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String md5(Object source,Object salt) {
	    //公有盐
		String globalSalt="1234waerasfaasafwr";
		//第一步：将明文进行md5消息摘要
		 try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(((String)source+salt+globalSalt).getBytes());
			
			//Base64
			Encoder encoder = Base64.getEncoder();
			String target = encoder.encodeToString(digest);
			return target;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String salt=UUID.randomUUID().toString();
		System.out.println(salt);
		System.out.println(md5("123456",salt));
	}

}
