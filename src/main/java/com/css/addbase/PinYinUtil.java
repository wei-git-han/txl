package com.css.addbase;

import com.css.base.utils.StringUtils;

public class PinYinUtil {
	/**
	 * 判断关键字中是否包含字母，是使用启首字母搜索
	 * @return
	 */
 public static boolean hasZm(String keywords) {
	 if(StringUtils.isEmpty(keywords)) {
		 return false;
	 }else {
		 return  keywords.matches(".*[a-zA-Z]+.*");
	 }
 }
 /**
  * 判断关键字中是否包含字母，是使用启首字母搜索
  * @return
  */
 public static boolean hasZm(String[] keywords) {
	 for(String keyword:keywords){
		 if(!StringUtils.isEmpty(keyword)&&keyword.matches(".*[a-zA-Z]+.*")) {
			 return true;
		 }
	 }
	 return false;
 }
}
