package com.css.txl.utils;
import com.css.base.utils.PingYinUtils;

public class ChineseFCUtil {
	private static int BEGIN = 45217;
	private static int END = 63486;
	private static char[] chartable =   {'吖','八','嚓','咑','妸','发','旮','妎','妎','丌','咔','垃','嘸','拏','筽','妑','七','亽','仨','侤','侤','侤','屲','夕','丫','帀'};
	private static int[] table = new int[27];
	private static char[] initaltable = {'a','b', 'c','d','e', 'f','g', 'h','h','j', 'k','l','m', 'n','o','p', 'q','r', 's','t','t','t', 'w','x','y','z'};
	static {
		for(int i =0; i < 26; i++) {
			table[i] = gbValue(chartable[i]);
		}
		table[26] = END;
	}
	
	public static String cn2py(String SourceStr) {
		String Result = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for(i = 0; i< StrLength; i++) {
				Result += Char2Initial(SourceStr.charAt(i));
			}
		}catch (Exception e) {
			// TODO: handle exception
			Result = "";
		}
		return Result;
	}
	
	private static char Char2Initial(char ch) {
		if(ch>= 'a' && ch <= 'z')
			return (char)(ch-'a'+'A');
		if(ch>= 'A' && ch <= 'Z')
			return ch;
		int gb = gbValue(ch);
		if((gb < BEGIN) || (gb > END))
			return ch;
		int i;
		for(i= 0; i< 26; i++) {
			if((gb>=table[i]) && (gb<table[i+1])) {
				break;
			}
		}
		if(gb == END) {
			i =25;
		}
		return initaltable[i];
	}
	
	private static int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB18030");
			if(bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		}catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(cn2py("荀攸").toUpperCase());
		System.out.println(PingYinUtils.getInstance().getSelling("荀攸"));
	}  
	
}
