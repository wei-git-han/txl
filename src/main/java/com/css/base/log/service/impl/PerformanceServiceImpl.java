package com.css.base.log.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.base.log.dao.PerformanceDao;
import com.css.base.log.entity.SysLogPerformance;
import com.css.base.log.service.PerformanceService;


@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {
	@Autowired
	private PerformanceDao performanceDao;
	
	@Override
	public SysLogPerformance queryObject(String projectId){
		return performanceDao.queryObject(projectId);
	}
	
	@Override
	public List<SysLogPerformance> queryList(Map<String, Object> map){
		return performanceDao.queryList(map);
	}
	
	@Override
	public void save(SysLogPerformance tProjectInfo){
		performanceDao.save(tProjectInfo);
	}
	
	@Override
	public void update(SysLogPerformance tProjectInfo){
		performanceDao.update(tProjectInfo);
	}
	
	@Override
	public void delete(SysLogPerformance projectId){
		performanceDao.delete(projectId);
	}

	
}
