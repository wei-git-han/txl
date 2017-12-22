package com.css.addbase.apporgmapped.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.addbase.apporgan.dao.BaseAppOrganDao;
import com.css.addbase.apporgan.dao.BaseAppUserDao;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgmapped.dao.BaseAppOrgMappedDao;
import com.css.addbase.apporgmapped.entity.BaseAppOrgMapped;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.utils.CurrentUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service("baseAppOrgMappedService")
public class BaseAppOrgMappedServiceImpl implements BaseAppOrgMappedService {
	@Autowired										
	private BaseAppOrgMappedDao baseAppOrgMappedDao;
	@Autowired
	private BaseAppOrganDao baseAppOrganDao;
	@Autowired
	private BaseAppUserDao baseAppUserDao;

	@Override
 	public Object orgMappedByOrgId(String appId,String orgId,String type){
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.isNotBlank(orgId)){
			map.put("orgId", orgId);
		}
		if(StringUtils.isNotBlank(appId)){
			map.put("appId", appId);
		}
		if(StringUtils.isNotBlank(type)){
			map.put("type", type);
		}
		
		List<BaseAppOrgMapped> list = baseAppOrgMappedDao.queryList(map);

		if(list==null||list.size()<=0){
			System.out.println("在表BASE_APP_ORG_MAPPED中，缺少配置信息，相关参数：appId="+appId+"，orgId="+orgId+"，type="+type);
			return null;
		}else	if( list.size() == 1 ){
			return list.get(0);
		}else{
			System.out.println("在表BASE_APP_ORG_MAPPED中，存在多条信息，相关参数：appId="+appId+"，orgId="+orgId+"，type="+type);
			return list;
		}
	}
	
	@Override
	public Object orgMapped(String appId, String userId, String type) {
		String bareauId = getBareauByUserId(userId);
		
		return orgMappedByOrgId(appId,bareauId,type);
	}
	

	@Override
	public String getBareauByUserId(String userId){
		if(StringUtils.isNotBlank(userId)){
			BaseAppUser user = baseAppUserDao.queryObject(userId);
			if(user != null){
				BaseAppOrgan org = baseAppOrganDao.queryObject(user.getOrganid());
				if(org != null){
					String[] pathArr = org.getTreePath().split(",");
					if(pathArr.length > 2){
						return pathArr[2];
					} else {
						return org.getId();
					}
				}
			}
		}
		
		return "";
	}

	@Override
	public String getUrlByType(String userId, String type) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", userId, type);
		if (bm != null) {
			return bm.getUrl();
		}
		return "";
	}

	@Override
	public String getWebUrl(String type, String uri) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", CurrentUser.getUserId(), type);
		if (bm != null) {
			return bm.getUrl() + (StringUtils.isNotBlank(bm.getWebUri()) ? bm.getWebUri() : "") + uri;
		}
		return "";
	}

	@Override
	public String getFileServerIdByType(String type) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", CurrentUser.getUserId(), type);
		if (bm != null) {
			return bm.getFileServer();
		}
		return "";
	}

	@Override
	public String getAppIdByUserId(String type) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", CurrentUser.getUserId(), type);
		if (bm != null) {
			return bm.getAppId();
		}
		return "";
	}

	@Override
	public String getOrgIdByUserId(String type) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", CurrentUser.getUserId(), type);
		if (bm != null) {
			return bm.getOrgId();
		}
		return "";
	}

	@Override
	public String getAppLevelByType(String type) {
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.isNotBlank(type)){
			map.put("type", type);
		}
		
		map.put("ministryApp", "1");
		
		List<BaseAppOrgMapped> list = baseAppOrgMappedDao.queryList(map);
		if (list != null && list.size() > 0) {
			return "true";
		}
		return "false";
	}

	@Override
	public Map<String, Object> getAppIdAndSecret(String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", CurrentUser.getUserId(), type);
		if (bm != null) {
			map.put("appId", bm.getAppId());
			map.put("appSecret", bm.getAppSecret());
		}
		return map;
	}
}
