package com.css.addbase.orgservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.css.addbase.AppConfig;
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
			System.out.println(e);
			AppConfig.accessToken = "";
			return (UserInfo) restTemplate.getForObject(
					appConfig.getZuul() + appConfig.getOrg() +"/userinfo/" + uuid + "?access_token=" + appConfig.getAccessToken(),
					UserInfo.class, new Object[0]);
		}
		
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
			StringBuffer url = new StringBuffer(appConfig.getZuul() + appConfig.getOrg() +"/department/" + id + "/children");
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
			StringBuffer url = new StringBuffer(appConfig.getZuul() + appConfig.getOrg() +"/department/" + id + "/children");
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
