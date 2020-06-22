package com.css.addbase.token;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.log.LogFormat;
import com.css.base.utils.DateUtil;
import com.css.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * token参数配置类
 * 
 * @author gengdesheng
 */
public class TokenConfig {

	// token有效时间 key：token value：过期时间戳
	private static HashMap<String, Long> tokenExpireTimes = new HashMap<String, Long>();
	// token信息 key：appid value：token
	private static HashMap<String, String> tokenInfos = new HashMap<String, String>();
	// 默认配置(application.yml中配置的token认证服务地址)
	private static String accessTokenUri;
	// 默认配置(application.yml中配置的appId)
	private static String clientId;
	// 默认配置(application.yml中配置的appSecret)
	private static String clientSecret;
	private static final String GRANT_TYPE = "client_credentials";

	// 日志记录
	private static Logger logger = LoggerFactory.getLogger(TokenConfig.class);

	/**
	 * 初始化配置文件参数
	 * 
	 * @return
	 */
	public TokenConfig(Environment env) {
		accessTokenUri = env.getProperty("csse.mircoservice.oauth2.client.accessTokenUri");
		clientId = env.getProperty("csse.mircoservice.oauth2.client.clientId");
		clientSecret = env.getProperty("csse.mircoservice.oauth2.client.clientSecret");
	}

	/**
	 * 系统默认token (即application.yml内配置的参数)
	 * 
	 * @return
	 */
	public static String token() {
		return getToken(clientId, clientSecret);
	}

	/**
	 * 获取token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String token(String appId, String appSecret) {

		if (StringUtils.isBlank(appId)) {
			appId = clientId;
			appSecret = clientSecret;
		}
		return getToken(clientId, clientSecret);

	}

	/**
	 * 根据应用的ID，获取token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return token
	 */
	public static String getToken(String appId, String appSecret) {
		String token = tokenInfos.get(appId);
		// token存在直接集合中的返回token且token未过期
		if (StringUtils.isNotBlank(token) && ckeckToken(token)) {
			logger.info(LogFormat.LOG_TOKEN,appId,appSecret,"返回","成功",token);
			return token;
		}
		;
		// token不存在获取token
		String accessTokenUrl = accessTokenUri + "?client_id=" + appId + "&client_secret=" + appSecret + "&grant_type="
				+ GRANT_TYPE;
		logger.info("token请求路径:{}",accessTokenUrl);
		try {
			RestTemplate restTemplate = new RestTemplate();
			JSONObject access_token = restTemplate.postForObject(accessTokenUrl, null, JSONObject.class);
			// 存在token
			token = access_token.getString("access_token");
			tokenInfos.put(appId, token);
			logger.info(LogFormat.LOG_TOKEN,appId,appSecret,"获取","成功",token);
			// 存储token过期时间
			String expires_in = access_token.getString("expires_in") + "000";
			long tokenExpireTime = System.currentTimeMillis() + Long.valueOf(expires_in);
			tokenExpireTimes.put(token, tokenExpireTime);
			logger.info("{}token过期时间:{}==={}",token,tokenExpireTime,DateUtil.timestampToDate(tokenExpireTime));
			restTemplate = null;
			return token;
		} catch (HttpClientErrorException e) {
			// 获取失败，返回null
			logger.info(LogFormat.LOG_TOKEN,appId,appSecret,"获取","失败","");
			System.err.println(e);
			return null;
		}
	}

	/**
	 * 验证token是否过期
	 * 
	 * @param token
	 * @return 过期:false,未过期:true
	 */
	public static boolean ckeckToken(String token) {
		Long tokenExpireTime = tokenExpireTimes.get(token);
		if (tokenExpireTime == null) {
			return false;
		}
		;
		Long remainingTime = tokenExpireTime - System.currentTimeMillis();
		// 如果过期剩余时间小于5分钟认为token已失效,从集合中删除
		logger.info("{}token剩余时间:{}ms",token,remainingTime);
		if (remainingTime < 300000) {
			tokenExpireTimes.remove(token);
			logger.info("{}token过期",token);
			return false;
		}
		return true;
	}
	/**
	 * 删除缓存的token
	 * 
	 * @param token
	 * @return 过期:false,未过期:true
	 */
	public static void removeToken(String token) {
	    tokenExpireTimes.remove(token);
	    logger.info("删除token:{}",token);
	}



}
