package com.css.txl.service.impl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.css.base.utils.UUIDUtils;
import com.css.txl.dao.TxlUserDao;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlUserService;

@Service("txlUserService")
public class TxlUserServiceImpl implements TxlUserService {
	@Autowired
	private TxlUserDao txlUserDao;
	
	@Override
	public TxlUser queryObject(String userid){
		return txlUserDao.queryObject(userid);
	}
	
	@Override
	public List<TxlUser> queryList(Map<String, Object> map){
		return txlUserDao.queryList(map);
	}
	
	@Override
	public void save(TxlUser item){
		String id=StringUtils.isBlank(item.getUserid())?UUIDUtils.random():item.getUserid();
		item.setUserid(id);
		txlUserDao.save(item);
	}
	
	@Override
	public void update(TxlUser item){
		txlUserDao.update(item);
	}
	
	@Override
	public void delete(String userid){
		txlUserDao.delete(userid);
	}
	
	@Override
	public void deleteBatch(String[] userids){
		txlUserDao.deleteBatch(userids);
	}
	@Override
	public int queryTotal(Map<String, Object> map) {
		return txlUserDao.queryTotal(map);
	}
	
	/**
	 * 根据部门Id获取人员信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public List<TxlUser> getUserInfos(String id) {
		return txlUserDao.getUserInfos(id);
	}
	
	/**
	 * 根据部门Id获取人员信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public List<TxlUser> getOthUsers(String id,String name) {
		return txlUserDao.getOthUsers(id,name);
	}
	
	public List<TxlUser> getNameToUser(String name){
		return txlUserDao.getNameToUser(name);
	}

	@Override
	public List<TxlUser> getTxlFavorite(String userId) {
		return txlUserDao.getTxlFavorite(userId);
	}

	@Override
	public void clearUser() {
		txlUserDao.clearUser();
	}

	@Override
	public void hideAllUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		txlUserDao.hiderAllUser(map);
	}

	@Override
	public List<TxlUser> queryListByOrganId(String organid) {
		// TODO Auto-generated method stub
		return txlUserDao.queryListByOrganId(organid);
	}
}
