package com.css.base.log.dao;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import com.css.base.log.entity.SysLogException;
@Mapper
public interface ExceptionDao extends BaseDao<SysLogException> {

	
}
