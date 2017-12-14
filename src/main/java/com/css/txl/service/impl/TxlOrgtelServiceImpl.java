package com.css.txl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.txl.dao.TxlOrgtelDao;
import com.css.txl.entity.TxlOrgtel;
import com.css.txl.service.TxlOrgtelService;



@Service("txlOrgtelService")
public class TxlOrgtelServiceImpl implements TxlOrgtelService {
	@Autowired
	private TxlOrgtelDao txlOrgtelDao;
	
	@Override
	public TxlOrgtel queryObject(String id){
		return txlOrgtelDao.queryObject(id);
	}
	
	@Override
	public List<TxlOrgtel> queryList(Map<String, Object> map){
		return txlOrgtelDao.queryList(map);
	}
	
	@Override
	public void save(TxlOrgtel txlOrgtel){
		txlOrgtelDao.save(txlOrgtel);
	}
	
	@Override
	public void update(TxlOrgtel txlOrgtel){
		txlOrgtelDao.update(txlOrgtel);
	}
	
	@Override
	public void delete(String id){
		txlOrgtelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		txlOrgtelDao.deleteBatch(ids);
	}
	
}
