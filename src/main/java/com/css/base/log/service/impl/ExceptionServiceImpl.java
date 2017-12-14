package com.css.base.log.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.base.log.dao.ExceptionDao;
import com.css.base.log.entity.SysLogException;
import com.css.base.log.service.ExceptionService;

@Service
@Transactional
public class ExceptionServiceImpl implements ExceptionService {
	
	@Autowired
	private ExceptionDao exceptionDao;
	
	@Override
	public SysLogException queryObject(String projectId) {
		SysLogException entity = exceptionDao.queryObject(projectId);
		return entity;
	}

	@Override
	public List<SysLogException> queryList(Map<String, Object> map) {
		List<SysLogException> list = exceptionDao.queryList(map);
		return list;
	}


	@Override
	public void save(SysLogException tProjectInfo) {
		exceptionDao.save(tProjectInfo);
		
	}

	@Override
	public void update(SysLogException ex_id) {
		exceptionDao.update(ex_id);
		
	}

	@Override
	public void delete(SysLogException ex_id) {
		exceptionDao.delete(ex_id);
	}
	
	
}
