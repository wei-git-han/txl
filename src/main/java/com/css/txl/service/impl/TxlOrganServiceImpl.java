package com.css.txl.service.impl;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.txl.dao.TxlUserDao;
import com.css.txl.entity.TxlUser;
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
	@Autowired
	private TxlUserDao txlUserDao;
	@Autowired
	private OrgService orgService;
	
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
	public void hideOrgan(Map<String, Object> map) {
		// TODO Auto-generated method stub
		txlOrganDao.hideOrgan(map);
	}

	@Override
	public String getBarOrgIdByUserId(String userId) {
		if(StringUtils.isNotBlank(userId)){
			TxlUser user = txlUserDao.queryObject(userId);
			if(user != null){
				TxlOrgan org = txlOrganDao.queryObject(user.getOrganid());
				if(org != null){
					String[] pathArr = org.getPath().split(",");
					if(pathArr.length > 2){
						return pathArr[2];
					} else {
						return org.getOrganid();
					}
				}
			} else {
				UserInfo userInfo = orgService.getUserInfo(userId);
				if(userInfo != null){
					Organ org = orgService.getOrgan(userInfo.getOrganId());
					if(org != null){
						String[] pathArr = org.getP().split(",");
						if(pathArr.length > 2){
							return pathArr[2];
						} else {
							return org.getOrganId();
						}
					}
				}
			}
		}
		return "";
	}

	@Override
	public List<String> queryListByTREEPATH(String organId) {
		// TODO Auto-generated method stub
		return txlOrganDao.queryListByTREEPATH(organId);
	}
}
