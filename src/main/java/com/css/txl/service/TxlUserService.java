package com.css.txl.service;

import java.util.List;
import java.util.Map;

import com.css.txl.entity.TxlUser;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-21 09:21:03
 */
public interface TxlUserService {
	
	TxlUser queryObject(String userid);
	
	List<TxlUser> queryList(Map<String, Object> map);
	
	void save(TxlUser item);
	
	void update(TxlUser item);
	
	void delete(String userid);
	
	void deleteBatch(String[] userids);
	
	List<TxlUser> getUserInfos(String id);
	
	List<TxlUser> getOthUsers(String id, String name);
	int queryTotal(Map<String,Object> map);
	List<TxlUser> getNameToUser(String name);

	List<TxlUser> getTxlFavorite(String userId);
	
	/**
	 * 清空组织人员
	 */
	void clearUser();
	/**
	 * 根据organId隐藏组织机构所有的下级用户
	 */
	void hideAllUser(Map<String,Object> map);
}
