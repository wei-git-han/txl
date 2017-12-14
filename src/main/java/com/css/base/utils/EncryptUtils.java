package com.css.base.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class EncryptUtils {
	
	/** 
	 * 获取MD2码 
	 * @return String 
	 * */ 
	public static String md2(byte[] data) {
		return DigestUtils.md2Hex(data);
	}
	
	/** 
	 * 获取MD2码 
	 * @return String 
	 * @throws IOException 
	 * */ 
	public static String md2(InputStream data) throws IOException {
		return DigestUtils.md2Hex(data);
	}
	
	/** 
	 * 获取MD2码 
	 * @return String 
	 * */ 
	public static String md2(String data) {
		return DigestUtils.md2Hex(data);
	}
	
	/** 
	 * 获取字符串MD5码 
	 * @param byte[] data
	 * @return String 
	 * */ 
	public static String md5(byte[] data) {
		return DigestUtils.md5Hex(data);
	}
	
	/** 
     * 获取MD5码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String md5(InputStream data) throws IOException {
		return DigestUtils.md5Hex(data);
	}
	
	/** 
	 * 获取MD5码 
	 * @return String 
	 * */ 
	public static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	/** 
     * 获取MD5值
     * @return String 
     * @throws IOException 
     */  
    public static String md5(FileInputStream data) throws IOException {  
    	return DigestUtils.md5Hex(IOUtils.toByteArray(data));
    } 
    
	/** 
     * 获取SHA1码 
     * @return String 
     * */ 
	public static String sha1(byte[] data) {
		return DigestUtils.sha1Hex(data);
	}
	
	/** 
	 * 获取SHA1码 
	 * @return String 
	 * @throws IOException 
	 * */ 
	public static String sha1(InputStream data) throws IOException {
		return DigestUtils.sha1Hex(data);
	}
	
	/** 
	 * 获取SHA1码 
	 * @return String 
	 * */ 
	public static String sha1(String data) {
		return DigestUtils.sha1Hex(data);
	}
	
	/** 
     * 获取SHA1码
     * @return String
	 * @throws IOException 
     * */  
    public static String sha1(FileInputStream data) throws IOException { 
    	return DigestUtils.sha1Hex(IOUtils.toByteArray(data));
    } 
    
	/** 
	 * 获取SHA256码 
	 * @return String 
	 * */ 
	public static String sha256(byte[] data) {
		return DigestUtils.sha256Hex(data);
	}
	
	/** 
     * 获取SHA256码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String sha256(InputStream data) throws IOException {
		return DigestUtils.sha256Hex(data);
	}
	
	/** 
	 * 获取SHA256码 
	 * @return String 
	 * */ 
	public static String sha256(String data) {
		return DigestUtils.sha256Hex(data);
	}
	
	/** 
	 * 获取SHA384码 
	 * @return String 
	 * */ 
	public static String sha384(byte[] data) {
		return DigestUtils.sha384Hex(data);
	}
	
	/** 
     * 获取SHA384码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String sha384(InputStream data) throws IOException {
		return DigestUtils.sha384Hex(data);
	}
	
	/** 
	 * 获取SHA384码 
	 * @return String 
	 * */ 
	public static String sha384(String data) {
		return DigestUtils.sha384Hex(data);
	}
	
	/** 
	 * 获取SHA512码 
	 * @return String 
	 * */ 
	public static String sha512(byte[] data) {
		return DigestUtils.sha512Hex(data);
	}
	
	/** 
     * 获取SHA512码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String sha512(InputStream data) throws IOException {
		return DigestUtils.sha512Hex(data);
	}
	
	/** 
	 * 获取SHA512码 
	 * @return String 
	 * */ 
	public static String sha512(String data) {
		return DigestUtils.sha512Hex(data);
	}
	
    /** 
     * 获取CRC32码 
     * @return String 
     * */  
    public static String crc32(FileInputStream data) {  
        CRC32 crc32 = new CRC32();  
        try {  
            byte[] buffer = new byte[8192];  
            int length;  
            while ((length = data.read(buffer)) != -1) {  
                crc32.update(buffer, 0, length);  
            }  
            return Long.toHexString(crc32.getValue()).toUpperCase();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return null;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            try {  
                if (data != null)  
                    data.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
}
