package com.css.apporgan.service;


import com.css.apporgan.entity.BaseAppOrgan;

import java.util.List;
import java.util.Map;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppOrganService {
	
	int queryTotal(Map<String, Object> map);
	
	BaseAppOrgan queryObject(String id);
	
	List<BaseAppOrgan> queryList(Map<String, Object> map);
	
	void save(BaseAppOrgan baseAppOrgan);
	
	void update(BaseAppOrgan baseAppOrgan);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	/**
	 * 根据父Id获取部门信息
	 * @author gengds
	 * @date 2017年11月29日
	 */
	List<BaseAppOrgan> findByParentId(String parentId);
	
	/**
	 * 根据ID获取该部门及其全部子部门信息
	 * @author gengds
	 * @date 2017年11月29日
	 */
	List<BaseAppOrgan> findAllDeptById(String deptId);
	
	/**
	 * 根据ID获取该部门及其全部子部门信息
	 * @author gengds
	 */
	List<BaseAppOrgan> findAllByparentId(String id);
	
	/**
	 * 清空组织机构
	 * @author gengds
	 */
	void clearOrgan();
	/**
	 * 根据下级组织机构id获取二级组织机构对象
	 */
	BaseAppOrgan getSecondary(String orgId);
	
	/**
	 * 根据id集合，获取部门
	 * @param id
	 * @return
	 */
	List<BaseAppOrgan> queryListByIds(String[] ids);
}
