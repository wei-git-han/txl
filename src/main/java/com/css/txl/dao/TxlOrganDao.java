package com.css.txl.dao;

import java.util.List;

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
}
