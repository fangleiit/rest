package com.biz.bizunited.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Describe Md5加解密
 * @Title Md5EncryptionAndDecryption
 * @author zkey
 * @Date 2016年6月5日 上午11:35:25
 * @Version v1.0
 */
public class Md5EncryptionAndDecryption {
	
	/**
	 * 使用md5加密
	 * @return
	 */
	public static String encryPwd(String password){
		//如果password为空直接返回null
		if(StringUtil.isEmpty(password)){
			return null;
		}
		
		String pwd = null;
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   
				    'a', 'b', 'c', 'd', 'e', 'f' };   
			MessageDigest md = MessageDigest.getInstance("MD5"); //获取md5加密实例
			md.update(password.getBytes());
			
	        //生成具体的md5密码
	        byte[] bypwd = md.digest();   
	        int j = bypwd.length;   
	        char str[] = new char[j * 2];   
	        int k = 0;   
	        for (int i = 0; i < j; i++) {   
	           byte byte0 = bypwd[i];   
	           str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
	           str[k++] = hexDigits[byte0 & 0xf];   
	        }   
	          return new String(str);   
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
		System.out.println(Md5EncryptionAndDecryption.encryPwd("123456"));
	}
}
