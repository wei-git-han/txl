package com.css.txl.dao;

import com.css.txl.entity.TxlUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.css.base.dao.BaseDao;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-21 09:21:03
 */
@Mapper
public interface TxlUserDao extends BaseDao<TxlUser> {
	/**
     * 根据子节点ID查询用户
     * @param id 
     */
	@Select("select * from TXL_USER where ORGANID = #{id} ")
	List<TxlUser> getUserInfos(String id);
	
	/**
     * 根据name查询用户
     * @param id 
     */
	@Select("select * from TXL_USER where FULLNAME like '%'||#{1}||'%' or ORGANNAME like '%'||#{1}||'%' or POST like '%'||#{1}||'%' or TELEPHONE like '%'||#{1}||'%' or MOBILE like '%'||#{1}||'%' or ADDRESS like '%'||#{1}||'%' or GETPY(FULLNAME) like upper(#{1}||'%')")
	List<TxlUser> getNameToUser(String name);
	
	/**
     * 根据name查询用户
     * @param id 
     */
	@Select("select * from TXL_USER where ORGANID = #{0} and (FULLNAME like '%'||#{1}||'%' or ORGANNAME like '%'||#{1}||'%' or POST like '%'||#{1}||'%' or TELEPHONE like '%'||#{1}||'%' or MOBILE like '%'||#{1}||'%' or ADDRESS like '%'||#{1}||'%' or GETPY(FULLNAME) like upper(#{1}||'%'))")
	List<TxlUser> getOthUsers(String id, String name);

	int queryTotal(Map<String, Object> map);
}
