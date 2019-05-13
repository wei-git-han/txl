package com.css.txl.dao;

import com.css.txl.entity.TxlRemark;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.css.base.dao.BaseDao;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-05-13 13:42:33
 */
@Mapper
public interface TxlRemarkDao extends BaseDao<TxlRemark> {

	TxlRemark queryObjectByRelation(@Param("remarkedPersonId")String remarkedPersonId, @Param("remarkCreatorId")String remarkCreatorId);
	
}
