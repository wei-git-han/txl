package com.css.txl.service;

import com.css.txl.entity.TxlOrgtel;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-25 15:08:05
 */
public interface TxlOrgtelService {
	
	TxlOrgtel queryObject(String id);
	
	List<TxlOrgtel> queryList(Map<String, Object> map);
	
	void save(TxlOrgtel txlOrgtel);
	
	void update(TxlOrgtel txlOrgtel);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
