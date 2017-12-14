package com.css.addbase.msg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.addbase.msg.dao.MsgTipDao;
import com.css.addbase.msg.entity.MsgTip;
import com.css.addbase.msg.service.MsgTipService;



@Service("msgTipService")
public class MsgTipServiceImpl implements MsgTipService {
	@Autowired
	private MsgTipDao msgTipDao;
	
	@Override
	public MsgTip queryObject(String msgId){
		return msgTipDao.queryObject(msgId);
	}
	
	@Override
	public List<MsgTip> queryList(Map<String, Object> map){
		return msgTipDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return msgTipDao.queryTotal(map);
	}
	
	@Override
	public void save(MsgTip msg){
		msgTipDao.save(msg);
	}
	
	@Override
	public void update(MsgTip msg){
		msgTipDao.update(msg);
	}
	
	@Override
	public void delete(String msgId){
		msgTipDao.delete(msgId);
	}
	
	@Override
	public void deleteBatch(String[] msgIds){
		msgTipDao.deleteBatch(msgIds);
	}
	
}
