package com.css.txl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.css.base.dao.BaseDao;
import com.css.txl.entity.TxlOrgan;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 21:01:42
 */
@Mapper
public interface TxlOrganDao extends BaseDao<TxlOrgan> {
	/**
     * 根据子节点ID查询状态
     * @param borrowStatus 借阅状态
     * @param id 文件ID
     */
	@Select("select * from TXL_ORGAN where FATHERID = #{id} and ISDELETE = 0 order by orderid")
	List<TxlOrgan> getSubOrg(String id);
	
	//@Select("select ORGANID, ORGANNAME, FATHERID, ORDERID, DN,(select count(0) from TXL_ORGAN a   WHERE a.FATHERID=b.ORGANID) AS  CODE, PATH, ORGUUID, TIMESTAMP, TYPE from TXL_ORGAN b where FATHERID = #{id} order by orderid")
	List<TxlOrgan> getSubOrgSync(Map<String, Object> map);
	
	/**
	 * 清空组织机构
	 * @author gengds
	 */
	@Delete("delete from TXL_ORGAN")
	void clearOrgan();
	@Update("update txl_organ set is_Show=#{isShow} where organid in (select organid from txl_organ start with organid=#{organId} and ISDELETE = 0 connect by prior organid=fatherid)")
	void hideOrgan(Map<String, Object> map);
}
