package com.css.addbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.css.base.utils.StringUtils;
@Configuration
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 系统取默认应用的token
	 */
	public static String accessToken;
	
	/**
	 * 根据appId和app的secret自取token
	 */
	public static String inviteAccessToken;
	
	/**
	 * 获取单点登录网关地址
	 * @return
	 */
	public String getZuul() {
		return env.getProperty("csse.mircoservice.zuul");
	}
	
	/**
	 * 单点登录接口
	 * @return
	 */
	public String getSso() {
		return env.getProperty("csse.mircoservice.sso");
	}
	
	/**
	 * 组织机构接口
	 * @return
	 */
	public String getOrg() {
		return env.getProperty("csse.mircoservice.org");
	}
	
	/**
	 * 消息服务接口
	 * @return
	 */
	public String getMsg() {
		return env.getProperty("csse.mircoservice.msg");
	}
	
	/**
	 * 鉴权服务接口
	 * @return
	 */
	public String getUaa() {
		return env.getProperty("csse.mircoservice.uaa");
	}
	
	/**
	 * 获取应用的appID
	 * @return
	 */
	public String getAppId() {
		return env.getProperty("csse.txl.appId");
	}
	
	/**
	 * 获取为服务token
	 * @return
	 */
	public String getAccessToken() {
		if (StringUtils.isNotEmpty(accessToken)) {
			return accessToken;
		} else {
			 InitAccessToken();
			 return accessToken;
		}
	}
	
	/**
	 * 根据应用的ID，获取token
	 * @param appId
	 * @return
	 */
	public String getAccessToken(String appId, String appSecret) {
		if (StringUtils.isNotEmpty(inviteAccessToken)) {
			return inviteAccessToken;
		} else {
			 InitAccessToken(appId, appSecret);
			 return inviteAccessToken;
		}
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public void InitAccessToken() {
		String accessTokenUri = env.getProperty("csse.mircoservice.oauth2.client.accessTokenUri");
		String clientId = env.getProperty("csse.mircoservice.oauth2.client.clientId");
		String clientSecret = env.getProperty("csse.mircoservice.oauth2.client.clientSecret");
		String grant_type = "client_credentials";
		String accessTokenUrl = accessTokenUri+"?client_id="+clientId+"&client_secret="+clientSecret+"&grant_type="+grant_type;
		JSONObject access_token = restTemplate.postForObject(accessTokenUrl,null,JSONObject.class);
		accessToken = access_token.getString("access_token");
	}
	
	/**
	 * 根据应用的appId 和 secret 获取应用的token
	 * @param appId
	 * @param appSecret
	 */
	public void InitAccessToken(String appId, String appSecret) {
		String accessTokenUri = env.getProperty("csse.mircoservice.oauth2.client.accessTokenUri");
		String grant_type = "client_credentials";
		String accessTokenUrl = accessTokenUri+"?client_id="+appId+"&client_secret="+appSecret+"&grant_type="+grant_type;
		JSONObject access_token = restTemplate.postForObject(accessTokenUrl,null,JSONObject.class);
		inviteAccessToken = access_token.getString("access_token");
	}
	
	/**
	 * 获取消息字符串
	 * @param title
	 * @param content
	 * @return
	 */
	public JSONObject getMsgJson(String title,String content,String redirect) {
		JSONObject msgContent = new JSONObject();
		msgContent.put("type", env.getProperty("msg.type"));
		msgContent.put("title",title);
		msgContent.put("content",content);
		msgContent.put("appid",env.getProperty("msg.appid"));
		//设置消息时间戳
		String time = String.valueOf(System.currentTimeMillis());
		msgContent.put("timestamp",Long.valueOf(time.substring(0, 10)));
		JSONObject action = new JSONObject();
		if (StringUtils.isNotEmpty(env.getProperty("msg.action.anstore"))) {
			if (StringUtils.equals(env.getProperty("msg.action.anstore"), "true")) {
       		 action.put("anstore", true);
       	    } else {
       		 action.put("anstore", false);
       	    }
		} else {
			action.put("anstore", false);
		}
         if (StringUtils.isNotEmpty(env.getProperty("msg.action.deployment"))) {
        	 if (StringUtils.equals(env.getProperty("msg.action.deployment"), "true")) {
        		 action.put("deployment", true);
        	 } else {
        		 action.put("deployment", false);
        	 }
		} else {
			action.put("deployment", false);
		}
        action.put("redirect", redirect);
        msgContent.put("action", action);
        
        
        JSONObject display = new JSONObject();
        if (StringUtils.isNotEmpty(env.getProperty("msg.display.msgbox"))) {
        	if (StringUtils.equals(env.getProperty("msg.display.msgbox"), "true")) {
        		display.put("msgbox", true);
        	} else {
        		display.put("msgbox", false);
        	}
        } else {
        	display.put("msgbox", true);
        }
        if (StringUtils.isNotEmpty(env.getProperty("msg.display.notification"))) {
        	if (StringUtils.equals(env.getProperty("msg.display.notification"), "true")) {
        		display.put("notification", true);
        	} else {
        		display.put("notification", false);
        	}
        } else {
        	display.put("notification", true);
        }
        if (StringUtils.isNotEmpty(env.getProperty("msg.display.system"))) {
        	if (StringUtils.equals(env.getProperty("msg.display.system"), "true")) {
        		display.put("system", true);
        	} else {
        		display.put("system", false);
        	}
        } else {
        	display.put("system", false);
        }
        msgContent.put("display", display);
		return msgContent;
	}
}
