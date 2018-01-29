package com.css.txl.service.impl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.css.base.utils.UUIDUtils;
import com.css.txl.dao.TxlOrganDao;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.service.TxlOrganService;

@Service("txlOrganService")
public class TxlOrganServiceImpl implements TxlOrganService {
	@Autowired
	private TxlOrganDao txlOrganDao;
	
	@Override
	public TxlOrgan queryObject(String organid){
		return txlOrganDao.queryObject(organid);
	}
	
	@Override
	public List<TxlOrgan> queryList(Map<String, Object> map){
		return txlOrganDao.queryList(map);
	}
	
	@Override
	public void save(TxlOrgan item){
		String id=StringUtils.isBlank(item.getOrganid())?UUIDUtils.random():item.getOrganid();
		item.setOrganid(id);
		txlOrganDao.save(item);
	}
	
	@Override
	public void update(TxlOrgan item){
		txlOrganDao.update(item);
	}
	
	@Override
	public void delete(String organid){
		txlOrganDao.delete(organid);
	}
	
	@Override
	public void deleteBatch(String[] organids){
		txlOrganDao.deleteBatch(organids);
	}
	/**
	 * 根据部门Id获取子部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public List<TxlOrgan> getSubOrg(String id) {
		return txlOrganDao.getSubOrg(id);
	}

	@Override
	public List<TxlOrgan> getSubOrgSync(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return txlOrganDao.getSubOrgSync(map);
	}

	@Override
	public void clearOrgan() {
		txlOrganDao.clearOrgan();
		
	}

	@Override
	public void hideUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		txlOrganDao.hideUser(map);
	}
}
