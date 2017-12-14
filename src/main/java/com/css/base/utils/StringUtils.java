package com.css.base.utils;

public class StringUtils extends org.apache.commons.lang.StringUtils{
	public static String arrToString(Object[] arr){
		String toString=arr.toString();
		String result=toString.substring(1,toString.length()-1);
		return result;
	}
}
