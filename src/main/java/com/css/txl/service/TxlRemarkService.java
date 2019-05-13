package com.css.txl.service;

import com.css.txl.entity.TxlRemark;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-05-13 13:42:33
 */
public interface TxlRemarkService {
	
	TxlRemark queryObject(String id);
	
	List<TxlRemark> queryList(Map<String, Object> map);
	
	void save(TxlRemark txlRemark);
	
	void update(TxlRemark txlRemark);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
