package com.css.txl.service.impl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.css.base.utils.UUIDUtils;
import com.css.txl.dao.TxlCollectDao;
import com.css.txl.entity.TxlCollect;
import com.css.txl.service.TxlCollectService;

@Service("txlCollectService")
public class TxlCollectServiceImpl implements TxlCollectService {
	@Autowired
	private TxlCollectDao txlCollectDao;
	
	@Override
	public TxlCollect queryObject(String id){
		return txlCollectDao.queryObject(id);
	}
	
	@Override
	public List<TxlCollect> queryList(Map<String, Object> map){
		return txlCollectDao.queryList(map);
	}
	
	@Override
	public void save(TxlCollect item){
		String id=StringUtils.isBlank(item.getId())?UUIDUtils.random():item.getId();
		item.setId(id);
		txlCollectDao.save(item);
	}
	
	@Override
	public void update(TxlCollect item){
		txlCollectDao.update(item);
	}
	
	@Override
	public void delete(String id){
		txlCollectDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		txlCollectDao.deleteBatch(ids);
	}
	/*@Override
	public int queryTotal(Map<String, Object> map) {
		return txlCollectDao.queryTotal(map);
	}*/
	
	public List<TxlCollect> getCollect(String id){
		return txlCollectDao.getCollect(id);
	}
	
	public TxlCollect getCollectUser(String id, String currentUserId){
		return txlCollectDao.getCollectUser(id, currentUserId);
	}

	@Override
	public List<TxlCollect> getCollect1(String id) {
		return txlCollectDao.getCollect1(id);
	}
}
