package com.css.appconfig.dao;

import com.css.appconfig.entity.BaseAppConfig;
import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
@Mapper
public interface BaseAppConfigDao extends BaseDao<BaseAppConfig> {
	
	int queryTotal(Map<String, Object> map);
	
	List<BaseAppConfig> typeList(String type);
	
	@Update("update BASE_APP_CONFIG set value=#{0} where type=#{1}")
	int UpdateValueByType(String value, String type);
	
	
}
