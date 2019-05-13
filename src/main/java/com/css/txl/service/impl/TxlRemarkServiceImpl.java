package com.css.txl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.txl.dao.TxlRemarkDao;
import com.css.txl.entity.TxlRemark;
import com.css.txl.service.TxlRemarkService;



@Service("txlRemarkService")
public class TxlRemarkServiceImpl implements TxlRemarkService {
	@Autowired
	private TxlRemarkDao txlRemarkDao;
	
	@Override
	public TxlRemark queryObject(String id){
		return txlRemarkDao.queryObject(id);
	}
	
	@Override
	public List<TxlRemark> queryList(Map<String, Object> map){
		return txlRemarkDao.queryList(map);
	}
	
	@Override
	public void save(TxlRemark txlRemark){
		txlRemarkDao.save(txlRemark);
	}
	
	@Override
	public void update(TxlRemark txlRemark){
		txlRemarkDao.update(txlRemark);
	}
	
	@Override
	public void delete(String id){
		txlRemarkDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		txlRemarkDao.deleteBatch(ids);
	}
	
}
