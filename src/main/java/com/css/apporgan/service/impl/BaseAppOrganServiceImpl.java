package com.css.apporgan.service.impl;

import com.css.apporgan.dao.BaseAppOrganDao;
import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.service.BaseAppOrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("baseAppOrganService")
public class BaseAppOrganServiceImpl implements BaseAppOrganService {
	@Autowired
	private BaseAppOrganDao baseAppOrganDao;
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return baseAppOrganDao.queryTotal(map);
	}
	
	@Override
	public BaseAppOrgan queryObject(String id){
		return baseAppOrganDao.queryObject(id);
	}
	
	@Override
	public List<BaseAppOrgan> queryList(Map<String, Object> map){
		return baseAppOrganDao.queryList(map);
	}
	
	@Override
	public void save(BaseAppOrgan baseAppOrgan){
		baseAppOrganDao.save(baseAppOrgan);
	}
	
	@Override
	public void update(BaseAppOrgan baseAppOrgan){
		baseAppOrganDao.update(baseAppOrgan);
	}
	
	@Override
	public void delete(String id){
		baseAppOrganDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		baseAppOrganDao.deleteBatch(ids);
	}

	@Override
	public List<BaseAppOrgan> findByParentId(String parentId) {
		return baseAppOrganDao.findByParentId(parentId);
	}

	@Override
	public List<BaseAppOrgan> findAllDeptById(String deptId) {
		return baseAppOrganDao.findAllDeptById(deptId);
	}

	@Override
	public void clearOrgan() {
		baseAppOrganDao.clearOrgan();
	}

	@Override
	public List<BaseAppOrgan> findAllByparentId(String id) {
		return baseAppOrganDao.findAllByparentId(id);
	}

	@Override
	public BaseAppOrgan getSecondary(String orgId) {
		// TODO Auto-generated method stub
		BaseAppOrgan organ=baseAppOrganDao.queryObject(orgId);
		if(organ!=null&&organ.getTreePath().split(",").length>2){
			return  baseAppOrganDao.queryObject(organ.getTreePath().split(",")[2]);
		}
		return null;
	}


	@Override
	public List<BaseAppOrgan> queryListByIds(String[] ids) {
		// TODO Auto-generated method stub
		return baseAppOrganDao.queryListByIds(ids);
	}

}
