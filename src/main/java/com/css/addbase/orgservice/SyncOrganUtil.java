package com.css.addbase.orgservice;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.css.addbase.AppConfig;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;

/**
 * 组织机构增量同步接口
 * @author gengds
 */
@Component
public class SyncOrganUtil {
	
	@Value("${csse.mircoservice.zuul}")
	private String zuul;
	
	@Value("${csse.mircoservice.syncdepartments}")
	private  String syncdepartments;
	
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	
	@Autowired
	private BaseAppUserService baseAppUserService;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static Long starttime;
	
	
	public SyncOrganUtil() {
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				if (starttime == null) {
					//设置消息时间戳
					String time = String.valueOf(System.currentTimeMillis());
					starttime = Long.valueOf(time.substring(0, 10));
				}
				//System.out.println("同步地址："+zuul+syncdepartments+"?starttime="+starttime+"&access_token=" + appConfig.getAccessToken());
				try {
					SyncOrgan syncOrgan = (SyncOrgan) restTemplate.getForObject(zuul+syncdepartments+"?starttime="+starttime+"&access_token=" + appConfig.getAccessToken(),
							SyncOrgan.class, new Object[0]);
					starttime = syncOrgan.getTimestamp();
					List<Organ> organs = syncOrgan.getOrg();
					if (organs != null && organs.size() > 0) {
						SyncOrgan(organs);
						//System.out.println("同步部门数:"+organs.size());
					}
					List<UserInfo> userInfos= syncOrgan.getUser();
					if (userInfos != null && userInfos.size() > 0) {
						SyncUser(userInfos);
						//System.out.println("同步用户数:"+userInfos.size());
					}
					//System.out.println("组织机构同步成功");
				} catch (Exception e) {
					//System.out.println("组织机构同步失败");
					System.out.println(e);
				}
			}
			
		}, 5000,300000);
	}
	
	/**
	 * 同步组织机构
	 * @param organs
	 */
    private void SyncOrgan(List<Organ> organs){
    	
    	for (Organ organ:organs) {
    		
    		if (StringUtils.equals(organ.getType() + "","0")) {
    			baseAppOrganService.delete(organ.getOrganId());
    		} else if (StringUtils.equals(organ.getType() + "","1")) {
    			BaseAppOrgan baseAppOrgan = baseAppOrganService.queryObject(organ.getOrganId());
    			baseAppOrgan = new BaseAppOrgan();
    			baseAppOrgan.setId(organ.getOrganId());
    			baseAppOrgan.setName(organ.getOrganName());
    			baseAppOrgan.setParentId(organ.getFatherId());
				baseAppOrgan.setTreePath(organ.getP());
				baseAppOrgan.setSort(organ.getOrderId());
				baseAppOrgan.setIsdelete(organ.getIsDelete());
				baseAppOrganService.update(baseAppOrgan);
    		} else if (StringUtils.equals(organ.getType() + "","2")) {
    			BaseAppOrgan baseAppOrgan = new BaseAppOrgan();
    			baseAppOrgan.setId(organ.getOrganId());
    			baseAppOrgan.setName(organ.getOrganName());
    			baseAppOrgan.setParentId(organ.getFatherId());
				baseAppOrgan.setTreePath(organ.getP());
				baseAppOrgan.setSort(organ.getOrderId());
				baseAppOrgan.setIsdelete(organ.getIsDelete());
				baseAppOrganService.save(baseAppOrgan);
    		}
    	}
    	
    }
    
    /**
     * 同步用户
     * @param userInfos
     */
    private void SyncUser(List<UserInfo> userInfos){
    	
    	for (UserInfo userInfo:userInfos) {
    		
            if (StringUtils.equals(userInfo.getType() + "","0")) {
            	baseAppUserService.deleteByUserId(userInfo.getUserid());
    		} else if (StringUtils.equals(userInfo.getType() + "","1")) {
    			BaseAppUser baseAppUser = baseAppUserService.queryObject(userInfo.getUserid());
    			baseAppUser.setId(userInfo.getUserid());
    			baseAppUser.setUserId(userInfo.getUserid());
    			baseAppUser.setAccount(userInfo.getAccount());
    			baseAppUser.setTruename(userInfo.getFullname());
    			baseAppUser.setMobile(userInfo.getMobile());
    			baseAppUser.setOrganid(userInfo.getOrganId());
    			baseAppUser.setSort(userInfo.getOrderId());
    			baseAppUser.setSex(userInfo.getSex());
    			baseAppUser.setIfSystem(0);
    			baseAppUser.setIsdelete(userInfo.getIsDelete());
				baseAppUserService.update(baseAppUser);
    		} else if (StringUtils.equals(userInfo.getType() + "","2")) {
    			BaseAppUser baseAppUser = new BaseAppUser();
    			baseAppUser.setId(userInfo.getUserid());
    			baseAppUser.setUserId(userInfo.getUserid());
    			baseAppUser.setAccount(userInfo.getAccount());
    			baseAppUser.setTruename(userInfo.getFullname());
    			baseAppUser.setMobile(userInfo.getMobile());
    			baseAppUser.setOrganid(userInfo.getOrganId());
    			baseAppUser.setSort(userInfo.getOrderId());
    			baseAppUser.setSex(userInfo.getSex());
    			baseAppUser.setIfSystem(0);
    			baseAppUser.setIsdelete(userInfo.getIsDelete());
				baseAppUserService.save(baseAppUser);
    		}
    	}
    }

}
