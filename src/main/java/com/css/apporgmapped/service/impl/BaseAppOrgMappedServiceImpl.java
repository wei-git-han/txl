package com.css.apporgmapped.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.appconfig.service.BaseAppConfigService;
import com.css.apporgan.dao.BaseAppOrganDao;
import com.css.apporgan.dao.BaseAppUserDao;
import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.entity.BaseAppUser;
import com.css.apporgmapped.dao.BaseAppOrgMappedDao;
import com.css.apporgmapped.entity.BaseAppOrgMapped;
import com.css.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.RestTemplateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

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
	@Autowired
	private OrgService orgService;
	@Autowired
	private BaseAppConfigService baseAppConfigService;

	@Override
 	public Object orgMappedByOrgId(String appLevel,String orgId,String type){
		List<BaseAppOrgMapped> list = getMappedData(appLevel,orgId,type);
		if(list==null||list.size()<=0){
			System.out.println("在表BASE_APP_ORG_MAPPED中，缺少配置信息，相关参数：appLevel="+appLevel+"，orgId="+orgId+"，type="+type);
			return null;
		}else if( list.size() == 1 ){
			return list.get(0);
		}else{
			System.out.println("在表BASE_APP_ORG_MAPPED中，存在多条信息，相关参数：appLevel="+appLevel+"，orgId="+orgId+"，type="+type);
			return list;
		}
	}
	
	public Object orgMappedByOrgId(String appId,String orgId,String type,String appLevel){
		List<BaseAppOrgMapped> list = getMappedData(appLevel,orgId,type);
		
		if(list==null||list.size()<=0){
			System.out.println("在表BASE_APP_ORG_MAPPED中，缺少配置信息，相关参数：appLevel="+appLevel+"，orgId="+orgId+"，type="+type);
			return null;
		}else if( list.size() == 1 ){
			return list.get(0);
		}else{
			System.out.println("在表BASE_APP_ORG_MAPPED中，存在多条信息，相关参数：appLevel="+appLevel+"，orgId="+orgId+"，type="+type);
			return list;
		}
	}
	
	@Override
	public Object orgMapped(String appId, String userId, String type) {
		String bareauId = getBareauByUserId(userId);
		
		return orgMappedByOrgId(appId,bareauId,type);
	}
	
	public Object orgMapped(String appId,String appLevel, String userId, String type) {
//		String bareauId = getBareauByUserId(userId);
		
		return orgMappedByOrgId(appId,null,type,appLevel);
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
			} else {
				UserInfo userInfo = orgService.getUserInfo(userId);
				if(userInfo != null){
					Organ org = orgService.getOrgan(userInfo.getOrganId());
					if(org != null){
						String[] pathArr = org.getP().split(",");
						if(pathArr.length > 2){
							return pathArr[2];
						} else {
							return org.getOrganId();
						}
					}
				}
			}
		}
		return "";
	}
	@Override
	public String getDetilOrgByUserId(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			BaseAppUser user = baseAppUserDao.queryObject(userId);
			if (user != null) {
				BaseAppOrgan org = baseAppOrganDao.queryObject(user.getOrganid());
				if (org != null) {
					return org.getId();
				} else {
					UserInfo userInfo = orgService.getUserInfo(userId);
					if (userInfo != null) {
						Organ organ = orgService.getOrgan(userInfo.getOrganId());
						if (organ != null) {
							return String.valueOf(organ.getOrderId());
						}
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
	public String getWebUrlByType(String type, String uri) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMappedByOrgId("",null,type);
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
		String ministryApp = baseAppConfigService.getValue(AppConstant.MINISTRY_APP);
		if (StringUtils.isBlank(ministryApp)) {
			return "false";
		}
		return ministryApp;
	}

	@Override
	public Map<String, Object> getAppIdAndSecret(String userId,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", userId, type);
		if (bm != null) {
			map.put("appId", bm.getAppId());
			map.put("appSecret", bm.getAppSecret());
		}
		return map;
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

	@Override
	public String getAppIdByUserId(String type, String userId) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", userId, type);
		if (bm != null) {
			return bm.getAppId();
		}
		return "";
	}

	@Override
	public String getWebUrlByAppLevel(String type,String uri , String AppLevel) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("",AppLevel ,CurrentUser.getUserId(), type);
		if (bm != null) {
			return bm.getUrl() + (StringUtils.isNotBlank(bm.getWebUri()) ? bm.getWebUri() : "") + uri;
		}
		return "";
	}
	
	@Override
	public String getBareauByUserIdss(String userId) {
		if(StringUtils.isNotBlank(userId)){
			BaseAppUser user = baseAppUserDao.queryObject(userId);
			if(user != null){
				BaseAppOrgan org = baseAppOrganDao.queryObject(user.getOrganid());
				if(org != null){
					String[] pathArr = org.getTreePath().split(",");
					if(pathArr.length > 3){
						return pathArr[3];
					} else {
						return org.getId();
					}
				}
			} else {
				UserInfo userInfo = orgService.getUserInfo(userId);
				if(userInfo != null){
					Organ org = orgService.getOrgan(userInfo.getOrganId());
					if(org != null){
						String[] pathArr = org.getP().split(",");
						if(pathArr.length > 3){
							return pathArr[3];
						} else {
							return org.getOrganId();
						}
					}
				}
			}
		}
		return "";
	}

	@Override
	public BaseAppOrgan getbyId(String Id) {
		return baseAppOrganDao.queryObject(Id);
	}
	
	@Override
	public List<BaseAppOrgMapped> getMappedData(String appLevel, String orgId, String type) {
		String gatewayConfig = baseAppConfigService.getValue(AppConstant.GATEWAY_CONFIG);
		//调用网关服务配置
		if (StringUtils.equals(gatewayConfig, "true")) {
			// TODO 调用网关服务配置
			LinkedMultiValueMap<String, Object> linkedMap = new LinkedMultiValueMap<String, Object>();
			if (StringUtils.isNotBlank(appLevel)) {
				linkedMap.add("appLevel", appLevel);
			}
			if (StringUtils.isNotBlank(orgId)) {
				linkedMap.add("orgId", orgId);
			}
			if (StringUtils.isNotBlank(type)) {
				linkedMap.add("type", type);
			}
			String url = baseAppConfigService.getValue(AppConstant.GATEWAY_URL)+Constant.GET_ORGMAPPED_LIST;
			String jsons= RestTemplateUtil.postJSONString(url, linkedMap);
			return JSONArray.parseArray(jsons, BaseAppOrgMapped.class);
		}
		// 查询本地数据库
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(orgId)){
			map.put("orgId", orgId);
		}
		if(StringUtils.isNotBlank(appLevel)){
			map.put("appLevel", appLevel);
		}
		if(StringUtils.isNotBlank(type)){
			map.put("type", type);
		}
		return baseAppOrgMappedDao.queryList(map);
	}

	@Override
	public String getWebUrl(AppType appType, String uri) {
		BaseAppOrgMapped bm = (BaseAppOrgMapped) this.orgMapped("", CurrentUser.getUserId(), appType);
		if (bm != null) {
			return bm.getUrl() + (StringUtils.isNotBlank(appType.getWebUri()) ? appType.getWebUri() : "") + uri;
		}
		return "";
	}
	
	@Override
	public Object orgMapped(String appLevel, String userId, AppType appType) {
		String bareauId = getBareauByUserId(userId);

		return orgMappedByOrgId(appLevel, bareauId, appType);
	}
	
	@Override
	public Object orgMappedByOrgId(String appLevel, String orgId, AppType appType) {
		List<BaseAppOrgMapped> list = getMappedData(appLevel,orgId,appType);
		if (list == null || list.size() <= 0) {
			System.out.println("在表BASE_APP_ORG_MAPPED中，缺少配置信息，相关参数：appLevel=" + appLevel + "，orgId=" + orgId + "，type="
					+ appType.getType());
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			System.out.println("在表BASE_APP_ORG_MAPPED中，存在多条信息，相关参数：appLevel=" + appLevel + "，orgId=" + orgId + "，type="
					+ appType.getType());
			return list;
		}
	}
	
	@Override
	public List<BaseAppOrgMapped> getMappedData(String appLevel, String orgId, AppType appType) {
		// 如果为单应用类型,清空orgId
		if (StringUtils.equals("one", appType.getAppFlag())) {
			orgId = null;
		}
		String gatewayConfig = baseAppConfigService.objectValue(AppConstant.GATEWAY_CONFIG);
		//调用网关服务配置
		if (StringUtils.equals(gatewayConfig, "true")) {
			// TODO 调用网关服务配置
			LinkedMultiValueMap<String, Object> linkedMap = new LinkedMultiValueMap<String, Object>();
			if (StringUtils.isNotBlank(appLevel)) {
				linkedMap.add("appLevel", appLevel);
			}
			if (StringUtils.isNotBlank(orgId)) {
				linkedMap.add("orgId", orgId);
			}
			if (StringUtils.isNotBlank(appType.getType())) {
				linkedMap.add("type", appType.getType());
			}
			String url = baseAppConfigService.objectValue(AppConstant.GATEWAY_URL)+Constant.GET_ORGMAPPED_LIST;
			String jsons= RestTemplateUtil.postJSONString(url, linkedMap);
			return JSONArray.parseArray(jsons, BaseAppOrgMapped.class);
		}
		// 查询本地数据库
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(appLevel)) {
			map.put("appLevel", appLevel);
		}
		if (StringUtils.isNotBlank(orgId)) {
			map.put("orgId", orgId);
		}
		if (StringUtils.isNotBlank(appType.getType())) {
			map.put("type", appType.getType());
		}
		return baseAppOrgMappedDao.queryList(map);
	}


	
}
