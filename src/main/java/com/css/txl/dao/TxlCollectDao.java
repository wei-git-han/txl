package com.css.txl.dao;

import com.css.txl.entity.TxlCollect;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.css.base.dao.BaseDao;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 20:26:21
 */
@Mapper
public interface TxlCollectDao extends BaseDao<TxlCollect> {
	/**
     * 查询当前用户收藏
     * @param id 
     */
	@Select("select t1.* from TXL_COLLECT t1 left join TXL_USER t2 on t1.collect_userid=t2.userid where t1.USERID = #{id} order by t2.orderid")
	List<TxlCollect> getCollect(String id);
	
	/**
     * 查询当前用户是否收藏某用户
     * @param id 
     */
	@Select("select * from TXL_COLLECT where COLLECT_USERID = #{0} and USERID = #{1}")
	TxlCollect getCollectUser(String id, String userid);
	
	/**
     * 查询当前用户收藏
     * @param id 
     */
	@Select("select t1.* from TXL_COLLECT t1 left join TXL_USER t2 on t1.COLLECT_USERID = t2.USERID where (t2.IS_SHOW IS NULL OR t2.IS_SHOW == '') and t1.USERID = #{id} order by t2.orderid")
	List<TxlCollect> getCollect1(String id);
}
