package com.css.txl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
	@Select("select * from TXL_ORGAN where FATHERID = #{id} order by orderid")
	List<TxlOrgan> getSubOrg(String id);
	
	@Select("select ORGANID, ORGANNAME, FATHERID, ORDERID, DN,(select count(0) from TXL_ORGAN a   WHERE a.FATHERID=b.ORGANID) AS  CODE, PATH, ORGUUID, TIMESTAMP, TYPE from TXL_ORGAN b where FATHERID = #{id} order by orderid")
	List<TxlOrgan> getSubOrgSync(String id);
	
	/**
	 * 清空组织机构
	 * @author gengds
	 */
	@Delete("delete from TXL_ORGAN")
	void clearOrgan();
}
