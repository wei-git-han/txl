package com.css.appconfig.service.impl;

import com.css.appconfig.dao.BaseAppConfigDao;
import com.css.appconfig.entity.BaseAppConfig;
import com.css.appconfig.service.BaseAppConfigService;
import com.css.constant.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("baseAppConfService")
public class BaseAppConfigServiceImpl implements BaseAppConfigService {
	@Autowired
	private BaseAppConfigDao baseAppConfigDao;

	@Override
	public int queryTotal(Map<String, Object> map) {
		return 0;
	}

	@Override
	public BaseAppConfig queryObject(String type) {
		return baseAppConfigDao.queryObject(type);
	}
	
	@Override
	public List<BaseAppConfig> typeList(String type) {
		return baseAppConfigDao.typeList(type);
	}
	@Override
	public String getValue(String type) {
		BaseAppConfig item=baseAppConfigDao.queryObject(type);
		return item!=null?item.getValue():null;
	}
	
	@Override
	public String getDlrFormat(String userName,String dlrName) {
		String ret = "";
		BaseAppConfig item=baseAppConfigDao.queryObject(AppConstant.DLR_FORMAT);
		if(item != null) {
			ret = item.getValue().replace("xxx", userName).replace("yyy", dlrName);
		}
		return ret;
	}

	@Override
	public int UpdateValueByType(String value, String type) {
		return baseAppConfigDao.UpdateValueByType(value, type);
	}

	@Override
	public String objectValue(String type) {
		String ret = "";
		BaseAppConfig config = baseAppConfigDao.queryObject(type);
		if(config != null) {
			ret = config.getValue();
		}
		return ret;
	}

}
