package com.css.addbase.orgservice;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.css.addbase.AppConfig;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
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
@RequestMapping("/app/timer")
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
	
	//自动同步时间
	private static Long starttime;
	//java 定时器
	private Timer timer = null;
	//定时器任务
	private static TimerTask timerTask = null;
	//定时器状态：true：定时器开启；false：定时器关闭
	private static boolean status = true;
	
	/**
	 * 启动程序时默认启动定时同步
	 */
	public SyncOrganUtil() {
		if (timer == null) {
			 timer = new Timer();
		}
		timer.scheduleAtFixedRate(getInstance(), 120000,300000);
	}
	/**
	 * 获取定时任务
	 * @return
	 */
	public  TimerTask getInstance() {
		if (timerTask == null || !status) {
			status = true;
			timerTask = new TimerTask(){
				@Override
				public void run() {
					SyncOrgan();
				}
			};
		}
		return timerTask;
	}
	
	/**
	 * 启动定时器
	 */
	@ResponseBody
	@RequestMapping("/start.htm")
	public void start() {
		if (!status) {
			timer.purge();
			timer = new Timer();
			timer.scheduleAtFixedRate(getInstance(), 120000,300000);
		}
	}
	/**
	 * 停止定时器
	 */
	@ResponseBody
	@RequestMapping("/cancel.htm")
	public void calcel() {
		timer.cancel();
		status = false;
	}
	
	/**
	 * 获取定时器状态
	 */
	@ResponseBody
	@RequestMapping("/status.htm")
	public void status() {
		if (status) {
			//定时器开启
			Response.json("status", true);
		} else {
			//定时器关闭
			Response.json("status",false);
		}
	}
	
	/**
	 * 实现增量同步接口
	 */
	public void SyncOrgan() {
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
			int syncNum = 0;
			if (organs != null && organs.size() > 0) {
				SyncOrgan(organs);
				syncNum += organs.size();
				System.out.println("同步部门数:"+organs.size());
			}
			List<UserInfo> userInfos= syncOrgan.getUser();
			if (userInfos != null && userInfos.size() > 0) {
				SyncUser(userInfos);
				syncNum += userInfos.size();
				System.out.println("同步用户数:"+userInfos.size());
			}
			System.out.println("组织机构同步成功：同步总数："+syncNum);
		} catch (Exception e) {
			System.out.println("组织机构同步失败");
			System.out.println(e);
		}
	}
	
	/**
	 * 同步组织机构
	 * @param organs
	 */
	public void SyncOrgan(List<Organ> organs){
    	
    	for (Organ organ:organs) {
    		if(StringUtils.equals("0", organ.getType())) {
    			//删除
				txlOrganService.delete(organ.getOrganId());
			} else if (StringUtils.equals("1", organ.getType()) || StringUtils.equals("2", organ.getType())){
				//编辑
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
			}
    	}
    	
    }
    
    /**
     * 同步用户
     * @param userInfos
     */
	public void SyncUser(List<UserInfo> userInfos){
    	
    	for (UserInfo userInfo:userInfos) {
    		
    		if(StringUtils.equals("0", userInfo.getType())) {
    			//人员删除
				txlUserService.delete(userInfo.getUserid());
			}else if(StringUtils.equals("1", userInfo.getType()) || StringUtils.equals("2", userInfo.getType())) {
				//人员编辑
				TxlUser txlUser = new TxlUser();
				txlUser.setIsdelete(String.valueOf(userInfo.getIsDelete()));
				txlUser.setOrderid(String.valueOf(userInfo.getOrderId()));
				txlUser.setAccount(userInfo.getAccount());
				txlUser.setDept(userInfo.getDept());
				txlUser.setPost((StringUtils.isNotBlank(userInfo.getDuty())&&(userInfo.getDuty().indexOf(";")!=-1))? userInfo.getDuty().split(";")[1]:"");
//				txlUser.setTelephone(userInfo.getTel());
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
//				txlUser.setMobile(userInfo.getMobile());
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
			}
    	}
      	
    }

}
