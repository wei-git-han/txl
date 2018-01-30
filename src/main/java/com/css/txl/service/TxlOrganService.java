package com.css.txl.service;

import com.css.txl.entity.TxlOrgan;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 21:01:42
 */
public interface TxlOrganService {
	
	TxlOrgan queryObject(String organid);
	
	List<TxlOrgan> queryList(Map<String, Object> map);
	
	void save(TxlOrgan item);
	
	void update(TxlOrgan item);
	
	void delete(String organid);
	
	void deleteBatch(String[] organids);
	
	List<TxlOrgan> getSubOrg(String id);
	List<TxlOrgan> getSubOrgSync(Map<String, Object> map);
	
	/**
	 * 清空组织机构
	 * @author gengds
	 */
	void clearOrgan();
	
	/*int queryTotal(Map<String,Object> map);*/
	void hideOrgan(Map<String, Object> map);
}
