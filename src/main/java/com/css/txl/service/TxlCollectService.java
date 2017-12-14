package com.css.txl.service;

import com.css.txl.entity.TxlCollect;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 20:26:21
 */
public interface TxlCollectService {
	
	TxlCollect queryObject(String id);
	
	List<TxlCollect> queryList(Map<String, Object> map);
	
	void save(TxlCollect item);
	
	void update(TxlCollect item);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	List<TxlCollect> getCollect(String id);
	/*int queryTotal(Map<String,Object> map);*/
	TxlCollect getCollectUser(String id, String currentUserId);
}
