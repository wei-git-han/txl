package com.css.webservice.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.JSONUtil;
import com.css.addbase.PinYinUtil;
import com.css.base.utils.Response;
import com.css.txl.service.TxlUserService;

@Controller
@RequestMapping("/api/search")
public class SearchApiController {
	@Autowired
	private TxlUserService txlUserService;
	@Value("${csse.txl.appId}")
	private String appId;
	/**
	 * 1、搜索范围：表[TXL_USER],字段：FULLNAME、MOBILE
	 * @param request
	 * @param json
	 */
	@ResponseBody
	@RequestMapping("/data")
	public void data(HttpServletRequest request) {
		sysPrint(request);
		JSONObject obj=jsonFailed(request);
		if(obj==null){return;}
		String[] keyWords=obj.getObject("keyWords", String[].class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keywords", keyWords);
		if (PinYinUtil.hasZm(keyWords)) {
			map.put("zm", "1");
		}
		int total=txlUserService.queryTotal(map);
		String link="/index.html?";
		JSONObject result=JSONUtil.resultSuccess(total,appId, link);
		Response.json(result);
	}
	private JSONObject jsonFailed(HttpServletRequest request) {
		String kds=request.getParameter("keyWords");
		String[] keyWords=toArray(kds);
		
		JSONObject jsonFailed = new JSONObject();
		jsonFailed.put("result", "failed");
		if (keyWords == null || keyWords.length <= 0) {
			jsonFailed.put("msg", "keyWords参数为空或不存在！");
			Response.json(jsonFailed);
			return null;
		}
		JSONObject json=new JSONObject();
		json.put("keyWords", keyWords);
		return json;
	}
	/**
	 * kds的格式：[u'123',u'ces']
	 * @param kds
	 * @return
	 */
	private static String[] toArray(String kds) {
		kds=unicode2String(kds);
		System.out.println("【keyWords】"+kds);
		String pattern="([^ \"]+)";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(kds);
		List<String> keyWords=new ArrayList<String>();
		while(m.find()){
			keyWords.add(m.group(1));
		}
		return keyWords.toArray(new String[]{});
	}
	/**
	 * 1、把unicode的编码字符串转换为该编码对应的字符串
	 * @param unicodeStr
	 * @return
	 */
	public static String unicode2String(String unicodeStr){
		Pattern p=Pattern.compile("(\\\\(\\p{XDigit}{4}))");
		char ch;
		Matcher m=p.matcher(unicodeStr);
		StringBuffer sb=new StringBuffer();
		while(m.find()){
			if(m.groupCount()>1){
				ch=(char)Integer.parseInt(m.group(2),16);
			}else{
				ch=(char)Integer.parseInt(m.group(1),10);
			}
			m.appendReplacement(sb, ch+"");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	private void sysPrint(HttpServletRequest request){
		Enumeration<String> enums=request.getParameterNames();
		while(enums.hasMoreElements()){
			String key=enums.nextElement();
			System.out.println("【搜索接口参数】"+key+"->"+toStr(request.getParameterValues(key)));
		}
	}
	
	private String toStr(String[] values) {
		StringBuilder sb=new StringBuilder();
		if(values!=null&&values.length>0){
			for(String value:values){
				sb.append(","+value);
			}
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}
}
