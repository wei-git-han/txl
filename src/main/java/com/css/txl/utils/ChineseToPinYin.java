package com.css.txl.utils;
import java.io.UnsupportedEncodingException;

public class ChineseToPinYin {
	public String getPYString(String str) {
		String tempStr ="";
		for(int i =0;i<str.length();i++) {
			char c = str.charAt(i);
			if(c>=33 && c<=126) {
				tempStr += String.valueOf(c);
			}else {
				tempStr += getPYChar(String.valueOf(c));
			}
		}
		return tempStr;
	}
	
	
	public String getPYChar(String c) {
		byte[] array = new byte[2];
		try {
			array = String.valueOf(c).getBytes("GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = (short)(array[0]-'\0'+256)*256 + ((short)(array[1]-'\0'+256));
		if(i<0xB0A1)
			return "*";
		if(i<0xB0C5)
			return "a";
		if(i<0xB0A1)
			return "a";
		if(i<0xB2C1)
			return "b";
		if(i<0xB4EE)
			return "c";
		if(i<0xB6EA)
			return "d";
		if(i<0xB7A2)
			return "e";
		if(i<0xB8C1)
			return "f";
		if(i<0xB9FE)
			return "g";
		if(i<0xBBF7)
			return "h";
		if(i<0xBFA6)
			return "j";
		if(i<0xC0AC)
			return "K";
		if(i<0xC2E8)
			return "l";
		if(i<0xC4C3)
			return "m";
		if(i<0xC5B6)
			return "n";
		if(i<0xC5BE)
			return "o";
		if(i<0xC6DA)
			return "p";
		if(i<0xC8BB)
			return "q";
		if(i<0xC8F6)
			return "r";
		if(i<0xCBFA)
			return "s";
		if(i<0xCDDA)
			return "t";
		if(i<0xCEF4)
			return "W";
		if(i<0xD1B9)
			return "x";
		if(i<0xD4D1)
			return "y";
		if(i<0xD7FA)
			return "z";
		return "*";
	}
	
//	public static void main(String[] args) {
//		ChineseToPinYin pinYin = new ChineseToPinYin();
//		/*Scanner sc = new Scanner(System.in);
//		System.out.println("请输入汉字");
//		String str = sc.next();*/
//		String py = pinYin.getPYString("荀攸");
//		System.out.println("拼音："+py);
//	}
}
