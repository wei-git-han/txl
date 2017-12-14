package com.css.addbase.msg.dao;

import com.css.addbase.msg.entity.MsgTip;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 消息信息表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-08-22 10:24:24
 */
@Mapper
public interface MsgTipDao extends BaseDao<MsgTip> {
	
	int queryTotal(Map<String,Object> map);
	
}
