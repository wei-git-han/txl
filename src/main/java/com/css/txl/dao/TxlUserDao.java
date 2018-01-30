package com.css.txl.dao;

import com.css.txl.entity.TxlUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	@Select("select * from TXL_USER where ORGANID = #{id} order by orderid")
	List<TxlUser> getUserInfos(String id);
	
	/**
     * 根据name查询用户
     * @param id 
     */
	@Select("select USERID,FULLNAME, ACCOUNT, PASSWORD, SEX, ORGANID,(SELECT ORGANNAME FROM TXL_ORGAN b WHERE b.ORGANID=a.ORGANID) as ORGANNAME,ORDERID, DN, ISDELETE, CA, ISMANAGER, TOKENID, SPID, SN, IP, STARTDATE, ENDDATE, USERUUID, USEREMAIL, SECLEVEL, FAILEDLOGINCOUNT, EDITPWDTIME, MOBILE, TIMESTAMP, TYPE, POST, TELEPHONE, ADDRESS,PYNAME from TXL_USER a where FULLNAME like '%'||#{1}||'%' or ORGANNAME like '%'||#{1}||'%' or POST like '%'||#{1}||'%' or TELEPHONE like '%'||#{1}||'%' or MOBILE like '%'||#{1}||'%' or ADDRESS like '%'||#{1}||'%' or GETPY(FULLNAME) like upper(#{1}||'%') order by orderid")
	List<TxlUser> getNameToUser(String name);
	
	/**
     * 根据name查询用户
     * @param id 
     */
	@Select("select * from TXL_USER where ORGANID = #{0} and (FULLNAME like '%'||#{1}||'%' or ORGANNAME like '%'||#{1}||'%' or POST like '%'||#{1}||'%' or TELEPHONE like '%'||#{1}||'%' or MOBILE like '%'||#{1}||'%' or ADDRESS like '%'||#{1}||'%' or GETPY(FULLNAME) like upper(#{1}||'%')) order by orderid")
	List<TxlUser> getOthUsers(String id, String name);

	int queryTotal(Map<String, Object> map);

	List<TxlUser> getTxlFavorite(String userId);
	
	/**
	 * 清空组织人员
	 */
	@Delete("delete from TXL_USER")
	void clearUser();
	@Update("update txl_user set is_show=#{isShow} where organid in (select organid from  txl_organ start with organid=#{organId} connect by prior organid=fatherid)")
	void hiderAllUser(Map<String, Object> map);
}
