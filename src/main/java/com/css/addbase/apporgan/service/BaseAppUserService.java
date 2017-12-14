package com.css.addbase.apporgan.service;

import com.css.addbase.apporgan.entity.BaseAppUser;
import java.util.List;
import java.util.Map;
/**
 * 人员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppUserService {
	
	int queryTotal(Map<String,Object> map);
	
	BaseAppUser queryObject(String id);
	
	List<BaseAppUser> queryList(Map<String, Object> map);
	
	void save(BaseAppUser baseAppUser);
	
	void update(BaseAppUser baseAppUser);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	/**
	 * 根据用户ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	List<BaseAppUser> findByUserId(String userId);
	
	/**
	 * 根据用户ID删除人员信息
	 * @author gengds
	 */
	List<BaseAppUser> deleteByUserId(String userId);
	
	/**
	 * 根据部门ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	List<BaseAppUser> findByDepartmentId(String organid);
	
	/**
	 * 根据部门ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	List<BaseAppUser> findByOrganid(String organid);
	
	/**
	 * 根据用户ID获取人员信息
	 * @param userIds
	 * @return
	 */
	List<BaseAppUser> queryObjectByUserIds(String[] userIds);
	
	/**
	 * 根据部门ID获取人员信息
	 * @param userIds
	 * @return
	 */
	List<BaseAppUser> queryObjectByDeptIds(String[] deptIds);
	
	/**
	 * 根据人员ID获取局级部门ID
	 * @param userIds
	 * @return
	 */
	String getBareauByUserId(String userId);
	
	/**
	 * 判断某个部门下是否存在人员
	 * @param deptId
	 * @return
	 */
	boolean queryCountUser(String deptId);
	/**
	 * 清空组织人员
	 */
	void clearUser();
}
