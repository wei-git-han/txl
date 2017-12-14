package com.css.addbase.msg;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.css.addbase.AppConfig;
import com.css.addbase.msg.entity.MsgTip;
import com.css.addbase.msg.service.MsgTipService;
/**
 * 消息服务接口
 * 
 * @author gengds
 */
@Service
public class MsgTipUtil {
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MsgTipService msgService;
	
	/**
	 * 给消息服务推送消息，进行消息提醒；
	 * @param title
	 * @param content
	 * @param userIds
	 * @return
	 */
	public String sendMsg(String title, String content, String url, String userIds, String appId, String appSecret){
		if (StringUtils.isBlank(userIds) || StringUtils.equals(userIds, ",")) {// 没有消息接收人，就不用提醒了
			return "fail";
		}
		if (StringUtils.isBlank(url)) {
			url = "http://{server}/index.html";
		}
		try {
			// 获取指定应用的token
			String accessToken = "";
			if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appSecret)) {
				accessToken = appConfig.getAccessToken(appId, appSecret);
				if (StringUtils.isBlank(accessToken)) {
					accessToken = appConfig.getAccessToken();
				}
			} else {
				accessToken = appConfig.getAccessToken();
			}
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("multipart/form-data");
			headers.setContentType(type);
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			map.add("content", appConfig.getMsgJson(title, content, url));
	        HttpEntity<LinkedMultiValueMap<String, Object>> formEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
	        String msgUrl = appConfig.getZuul() + appConfig.getMsg() + "/message/user/" + userIds + "?access_token=" + accessToken;
			Object result = restTemplate.postForEntity(msgUrl, formEntity, String.class);
			System.out.println(map.toString());
			System.out.println(result);
			if (result != null) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e);
			return "fail";
		}
	}
	
	/**
	 * 发送消息
	 * @param path 发送路径
	 * @param title 消息标题
	 * @param content 消息内容
	 * @return
	 */
	public  String sengMsg(String path,String msgId) {
		MsgTip msg = msgService.queryObject(msgId);
		if (msg == null) {
			System.out.println("消息ID错误");
			return "fail";
		}
		try{
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("multipart/form-data");
			headers.setContentType(type);
//			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//			headers.add("access_token", appConfig.getAccessToken());
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			System.out.println(appConfig.getMsgJson(msg.getMsgTitle(), msg.getMsgContent(),msg.getMsgRedirect()));
			map.add("content", appConfig.getMsgJson(msg.getMsgTitle(), msg.getMsgContent(),msg.getMsgRedirect()));
	        HttpEntity<LinkedMultiValueMap<String, Object>> formEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
	        System.out.println(map.toString());
	        String msgUrl = appConfig.getZuul()+appConfig.getMsg()+path+"?access_token="+appConfig.getAccessToken();
			Object result1 = restTemplate.postForEntity(msgUrl, formEntity,String.class);
			System.out.println(result1);
		}catch(Exception e) {
			System.out.println(e);
			try{
			AppConfig.accessToken = "";
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("multipart/form-data");
			headers.setContentType(type);
//			headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE.toString());
//			headers.add("access_token", appConfig.getAccessToken());
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			System.out.println(appConfig.getMsgJson(msg.getMsgTitle(), msg.getMsgContent(),msg.getMsgRedirect()));
			map.add("content", appConfig.getMsgJson(msg.getMsgTitle(), msg.getMsgContent(),msg.getMsgRedirect()));
	        HttpEntity<LinkedMultiValueMap<String, Object>> formEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
	        System.out.println(map.toString());
	        String msgUrl = appConfig.getZuul()+appConfig.getMsg()+path+"?access_token="+appConfig.getAccessToken();
			Object result1 = restTemplate.postForEntity(msgUrl, formEntity,String.class);
			System.out.println(result1);
			}catch(Exception ex) {
				System.out.println(ex);
				return "fail";
			}
		}
		return "success";
	}

}
