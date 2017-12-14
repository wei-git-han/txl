package com.css.addbase.msg.service;

import com.css.addbase.msg.entity.MsgTip;

import java.util.List;
import java.util.Map;

/**
 * 消息信息表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-08-22 10:24:24
 */
public interface MsgTipService {
	
	MsgTip queryObject(String msgId);
	
	List<MsgTip> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String,Object> map);
	
	void save(MsgTip msg);
	
	void update(MsgTip msg);
	
	void delete(String msgId);
	
	void deleteBatch(String[] msgIds);
}
