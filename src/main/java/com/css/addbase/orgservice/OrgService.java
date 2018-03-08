package com.css.addbase.orgservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.base.entity.SSOUser;
/**
 * 核心服务组织机构接口
 * 
 * @author gengds
 */
@Service
public class OrgService {

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 根据用户Id获取用户信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public UserInfo getUserInfo(String uuid) {
		try {
			return (UserInfo) restTemplate.getForObject(
					appConfig.getZuul() + appConfig.getOrg() +"/userinfo/" + uuid + "?access_token=" + appConfig.getAccessToken(),
					UserInfo.class, new Object[0]);
		} catch (Exception e) {
			System.out.println("【报错信息】用户ID不存在，userId="+uuid);
			System.out.println(e);
			AppConfig.accessToken = "";
			return (UserInfo) restTemplate.getForObject(
					appConfig.getZuul() + appConfig.getOrg() +"/userinfo/" + uuid + "?access_token=" + appConfig.getAccessToken(),
					UserInfo.class, new Object[0]);
		}
		
	}
	/*
	 * 根据用户token获取用户信息
	 */
	public  SSOUser getSUser(String token){
		String url=appConfig.getZuul()  +"/api/sso/user/" ;
		SSOUser user=null;
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			OutputStream os =  con.getOutputStream();
			String body ="access_token="+ token;
			os.write(body.getBytes());
			os.flush();
			InputStream is = con.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line  = reader.readLine();
			reader.close();
			user=JSONObject.parseObject(line,SSOUser.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user ;
	}
	/**
	 * 根据部门Id获取部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public Organ getOrgan(String id) {
		try {
			return  (Organ) restTemplate.getForObject(
					appConfig.getZuul() + appConfig.getOrg() +"/department/" + id + "?access_token=" + appConfig.getAccessToken(),
					Organ.class, new Object[0]);
		} catch (Exception e) {
			System.out.println("【报错信息】组织机构ID不存在，orgId="+id);
			System.out.println(e);
			AppConfig.accessToken = "";
			return  (Organ) restTemplate.getForObject(
					appConfig.getZuul() + appConfig.getOrg() +"/department/" + id + "?access_token=" + appConfig.getAccessToken(),
					Organ.class, new Object[0]);
		}
		
	}

	/**
	 * 根据部门Id获取子部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public Organ[] getSubOrg(String id) {
		return getSubOrg(id, false, false);
	}

	/**
	 * 根据部门Id获取子部门信息 包含无效数据：invalid 包含全部子级：sublevel
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public Organ[] getSubOrg(String id, boolean invalid, boolean sublevel) {
		try {
			StringBuffer url = new StringBuffer(appConfig.getZuul() + appConfig.getOrg() +"/department/" + id + "/subdepartments");
			if ((invalid) || (sublevel)) {
				url.append("?");
				if (invalid) {
					url.append("invalid&");
				}
				if (sublevel) {
					url.append("sublevel&");
				}
				url.append("access_token=" + appConfig.getAccessToken());
			} else {
				url.append("?access_token=" + appConfig.getAccessToken());
			}
			return (Organ[]) restTemplate.getForObject(url.toString(), Organ[].class, new Object[0]);
		} catch (Exception e) {
			System.out.println(e);
			AppConfig.accessToken = "";
			StringBuffer url = new StringBuffer(appConfig.getZuul() + appConfig.getOrg() +"/department/" + id + "/subdepartments");
			if ((invalid) || (sublevel)) {
				url.append("?");
				if (invalid) {
					url.append("invalid&");
				}
				if (sublevel) {
					url.append("sublevel&");
				}
				url.append("access_token=" + appConfig.getAccessToken());
			} else {
				url.append("?access_token=" + appConfig.getAccessToken());
			}
			return (Organ[]) restTemplate.getForObject(url.toString(), Organ[].class, new Object[0]);
		}
		
	}

	/**
	 * 根据部门Id获取部门用户信息 包含无效数据：invalid 包含全部子级：sublevel
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public UserInfo[] getUserInfos(String orgid) {
		return getUserInfos(orgid, false, false);
	}

	/**
	 * 根据部门Id获取部门用户信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public UserInfo[] getUserInfos(String orgid, boolean invalid, boolean sublevel) {
		try {
			StringBuffer url = new StringBuffer(appConfig.getZuul() + appConfig.getOrg() +"/department/" + orgid + "/userinfos");
			if ((invalid) || (sublevel)) {
				url.append("?");
				if (invalid) {
					url.append("invalid&");
				}
				if (sublevel) {
					url.append("sublevel&");
				}
				url.append("access_token=" + appConfig.getAccessToken());
			} else {
				url.append("?access_token=" + appConfig.getAccessToken());
			}
			return  (UserInfo[]) restTemplate.getForObject(url.toString(), UserInfo[].class, new Object[0]);
		} catch (Exception e) {
			System.out.println(e);
			AppConfig.accessToken = "";
			StringBuffer url = new StringBuffer(appConfig.getZuul() + appConfig.getOrg() +"/department/" + orgid + "/userinfos");
			if ((invalid) || (sublevel)) {
				url.append("?");
				if (invalid) {
					url.append("invalid&");
				}
				if (sublevel) {
					url.append("sublevel&");
				}
				url.append("access_token=" + appConfig.getAccessToken());
			} else {
				url.append("?access_token=" + appConfig.getAccessToken());
			}
			return  (UserInfo[]) restTemplate.getForObject(url.toString(), UserInfo[].class, new Object[0]);
		}
		
	}

}
