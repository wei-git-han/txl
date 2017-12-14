package com.css.base.log.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.base.log.dao.OperatorLogDao;
import com.css.base.log.entity.SysLogOperator;
import com.css.base.log.service.OperatorLogService;


@Service
@Transactional
public class OperatorLogServiceImpl implements OperatorLogService {
	@Autowired
	private OperatorLogDao operatorLogDao;
	
	@Override
	public SysLogOperator queryObject(String projectId){
		return operatorLogDao.queryObject(projectId);
	}
	
	@Override
	public List<SysLogOperator> queryList(Map<String, Object> map){
		return operatorLogDao.queryList(map);
	}
	
	@Override
	public void save(SysLogOperator tProjectInfo){
		operatorLogDao.save(tProjectInfo);
	}
	
	@Override
	public void update(SysLogOperator tProjectInfo){
		operatorLogDao.update(tProjectInfo);
	}
	
	@Override
	public void delete(SysLogOperator projectId){
		operatorLogDao.delete(projectId);
	}
	

}
