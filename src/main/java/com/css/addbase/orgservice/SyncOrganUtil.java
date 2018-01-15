package com.css.addbase.orgservice;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.css.addbase.AppConfig;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;
import com.css.txl.utils.ChineseFCUtil;

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
	private TxlOrganService txlOrganService;
	
	@Autowired
	private TxlUserService txlUserService;
	
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
				System.out.println("同步地址："+zuul+syncdepartments+"?starttime="+starttime+"&access_token=" + appConfig.getAccessToken());
				try {
					SyncOrgan syncOrgan = (SyncOrgan) restTemplate.getForObject(zuul+syncdepartments+"?starttime="+starttime+"&access_token=" + appConfig.getAccessToken(),
							SyncOrgan.class, new Object[0]);
					starttime = syncOrgan.getTimestamp();
					List<Organ> organs = syncOrgan.getOrg();
					if (organs != null && organs.size() > 0) {
						SyncOrgan(organs);
						System.out.println("同步部门数:"+organs.size());
					}
					List<UserInfo> userInfos= syncOrgan.getUser();
					if (userInfos != null && userInfos.size() > 0) {
						SyncUser(userInfos);
						System.out.println("同步用户数:"+userInfos.size());
					}
					System.out.println("组织机构同步成功");
				} catch (Exception e) {
					System.out.println("组织机构同步失败");
					System.out.println(e);
				}
			}
			
		}, 120000,600000);
	}
	
	/**
	 * 同步组织机构
	 * @param organs
	 */
    private void SyncOrgan(List<Organ> organs){
    	
    	for (Organ organ:organs) {
    		if(StringUtils.equals("0", organ.getType())) {
				txlOrganService.delete(organ.getOrganId());
			} else if (StringUtils.equals("1", organ.getType())){
				TxlOrgan txlOrgan = new TxlOrgan();
				txlOrgan.setCode(organ.getCode());
				txlOrgan.setIsdelete(String.valueOf(organ.getIsDelete()));
				txlOrgan.setOrderid(String.valueOf(organ.getOrderId()));
				txlOrgan.setDn(organ.getDn());
				txlOrgan.setFatherid(organ.getFatherId());
				txlOrgan.setOrganid(organ.getOrganId());
				txlOrgan.setOrganname(organ.getOrganName());
				txlOrgan.setOrguuid(organ.getOrguuid());
				txlOrgan.setType(organ.getType());
				txlOrgan.setTimestamp(organ.getTimestamp());
				TxlOrgan txlOrgantemp = txlOrganService.queryObject(organ.getOrganId());
				if (txlOrgantemp == null) {
					txlOrganService.save(txlOrgan);
				} else {
					txlOrganService.update(txlOrgan);
				} 
			}else {
				TxlOrgan txlOrgan = new TxlOrgan();
				txlOrgan.setCode(organ.getCode());
				txlOrgan.setIsdelete(String.valueOf(organ.getIsDelete()));
				txlOrgan.setOrderid(String.valueOf(organ.getOrderId()));
				txlOrgan.setDn(organ.getDn());
				txlOrgan.setFatherid(organ.getFatherId());
				txlOrgan.setOrganid(organ.getOrganId());
				txlOrgan.setOrganname(organ.getOrganName());
				txlOrgan.setOrguuid(organ.getOrguuid());
				txlOrgan.setType(organ.getType());
				txlOrgan.setTimestamp(organ.getTimestamp());
				txlOrganService.save(txlOrgan);
			}
    	}
    	
    }
    
    /**
     * 同步用户
     * @param userInfos
     */
    private void SyncUser(List<UserInfo> userInfos){
    	
    	for (UserInfo userInfo:userInfos) {
    		
    		if(StringUtils.equals("0", userInfo.getType())) {
				txlUserService.delete(userInfo.getUserid());
			}else if(StringUtils.equals("1", userInfo.getType())) {
				TxlUser txlUser = new TxlUser();
				txlUser.setIsdelete(String.valueOf(userInfo.getIsDelete()));
				txlUser.setOrderid(String.valueOf(userInfo.getOrderId()));
				txlUser.setAccount(userInfo.getAccount());
				txlUser.setDept(userInfo.getDept());
				txlUser.setPost(userInfo.getDuty());
				txlUser.setTelephone(userInfo.getTel());
				if(null != userInfo.getFullname() && !"".equals(userInfo.getFullname())) {
					txlUser.setFullname(userInfo.getFullname());
					if(userInfo.getFullname().indexOf("首长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"SZ");
					}else if(userInfo.getFullname().indexOf("局长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"JZ");
					}else if(userInfo.getFullname().indexOf("处长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"CZ");
					}else {
						txlUser.setPyName(ChineseFCUtil.cn2py(userInfo.getFullname()).toUpperCase());
					}
				}
				txlUser.setMobile(userInfo.getMobile());
				txlUser.setOrganid(userInfo.getOrganId());
				txlUser.setPassword(userInfo.getPassword());
				txlUser.setSeclevel(userInfo.getSecLevel());
				txlUser.setSex(userInfo.getSex());
				txlUser.setUseremail(userInfo.getUserEmail());
				txlUser.setUserid(userInfo.getUserid());
				TxlUser txlUsertemp = txlUserService.queryObject(userInfo.getUserid());
                if (txlUsertemp == null) {
                	txlUserService.save(txlUser);
                } else {
                	txlUserService.update(txlUser);
                }
			}else {
				//人员新增
				TxlUser txlUser = new TxlUser();
				txlUser.setIsdelete(String.valueOf(userInfo.getIsDelete()));
				txlUser.setOrderid(String.valueOf(userInfo.getOrderId()));
				txlUser.setAccount(userInfo.getAccount());
				txlUser.setDept(userInfo.getDept());
				txlUser.setPost(userInfo.getDuty());
				txlUser.setTelephone(userInfo.getTel());
				if(null != userInfo.getFullname() && !"".equals(userInfo.getFullname())) {
					txlUser.setFullname(userInfo.getFullname());
					if(userInfo.getFullname().indexOf("首长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"SZ");
					}else if(userInfo.getFullname().indexOf("局长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"JZ");
					}else if(userInfo.getFullname().indexOf("处长") > 0) {
						txlUser.setPyName(ChineseFCUtil.cn2py(String.valueOf(userInfo.getFullname().charAt(0))).toUpperCase()+"CZ");
					}else {
						txlUser.setPyName(ChineseFCUtil.cn2py(userInfo.getFullname()).toUpperCase());
					}
				}
				txlUser.setMobile(userInfo.getMobile());
				txlUser.setOrganid(userInfo.getOrganId());
				txlUser.setPassword(userInfo.getPassword());
				txlUser.setSeclevel(userInfo.getSecLevel());
				txlUser.setSex(userInfo.getSex());
				txlUser.setUseremail(userInfo.getUserEmail());
				txlUser.setUserid(userInfo.getUserid());
				txlUserService.save(txlUser);
			}
    	}
      	
    }

}
