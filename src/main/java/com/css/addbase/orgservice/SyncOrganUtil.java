package com.css.addbase.orgservice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.css.addbase.AppConfig;
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
	private static Logger logger = LoggerFactory.getLogger(SyncOrganUtil.class);
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
	//java 定时器2
	private Timer timer2 = null;
	//定时器任务
	private static TimerTask timerTask = null;
	private static TimerTask timerTask2 = null;
	//定时器状态：true：定时器开启；false：定时器关闭
	private static boolean status = true;
	private static boolean status2 = true;
	private static int num = 1;
	
	/**
	 * 启动程序时默认启动定时同步
	 * @throws InterruptedException 
	 */
	public SyncOrganUtil() throws InterruptedException{
		if (timer == null) {
			 timer = new Timer();
		}
		timer.scheduleAtFixedRate(getInstance(), 12000,300000);
		if (timer2 == null) {
			 timer2 = new Timer();
		}
		timer2.scheduleAtFixedRate(getInstance2(), 10,3600000);
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
					try{
						SyncOrgan();
					}catch (Exception e){
						e.printStackTrace();
						logger.info("增量同步接口异常{}", com.css.base.utils.StringUtils.isBlank(e.getMessage()) ? "请看后台日志："+e : e.getMessage());
					}

				}
			};
		}
		return timerTask;
	}
	
	public  TimerTask getInstance2() {
		if (timerTask2 == null || !status2) {
			status2 = true;
			timerTask2 = new TimerTask(){
				@Override
				public void run() {
					try{
						SyncTxlUser();
					}catch (Exception e){
						e.printStackTrace();
						logger.info("通讯录同步接口异常{}", com.css.base.utils.StringUtils.isBlank(e.getMessage()) ? "请看后台日志："+e : e.getMessage());
					}

				}
			};
		}
		return timerTask2;
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
			timer2.purge();
			timer2 = new Timer();
			timer2.scheduleAtFixedRate(getInstance2(), 10,3600000);
		}
	}
	/**
	 * 停止定时器
	 */
	@ResponseBody
	@RequestMapping("/cancel.htm")
	public void calcel() {
		timer.cancel();
		timer2.cancel();
		status = false;
		status2 = false;
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
	 * 获取定时器状态
	 */
	@ResponseBody
	@RequestMapping("/status2.htm")
	public void status2() { 
		if (status2) {
			//定时器开启
			Response.json("status2", true);
		} else {
			//定时器关闭
			Response.json("status2",false);
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
				txlUser.setOrderid(String.valueOf(userInfo.getRelations().get(0).get("orderId")));
				txlUser.setAccount(userInfo.getAccount());
				txlUser.setDept(userInfo.getDept());
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
				txlUser.setOrganid(userInfo.getOrganId());
				txlUser.setOrganid(userInfo.getRelations().get(0).get("organId"));
				txlUser.setPassword(userInfo.getPassword());
				txlUser.setSeclevel(userInfo.getSecLevel());
				txlUser.setSex(userInfo.getSex());
				txlUser.setUseremail(userInfo.getUserEmail());
				txlUser.setUserid(userInfo.getUserid());
				TxlUser txlUsertemp = txlUserService.queryObject(userInfo.getUserid());
				if (txlUsertemp == null) {
                	//txlUser.setPost((StringUtils.isNotBlank(userInfo.getDuty())&&(userInfo.getDuty().indexOf(";")!=-1))? userInfo.getDuty().split(";")[1]:"");
                	txlUser.setTelephone(userInfo.getTel());
                	txlUser.setMobile(userInfo.getMobile());
                	txlUserService.save(txlUser);
                } else {
                	/*if (StringUtils.isEmpty(txlUsertemp.getPost())) {
                		txlUser.setPost((StringUtils.isNotBlank(userInfo.getDuty())&&(userInfo.getDuty().indexOf(";")!=-1))? userInfo.getDuty().split(";")[1]:"");
					}*/
                	txlUser.setPost(txlUsertemp.getPost());
					txlUser.setTelephone(txlUsertemp.getTelephone());
					txlUser.setMobile(userInfo.getMobile());
    				txlUser.setMobileTwo(txlUsertemp.getMobileTwo());
    				txlUser.setTelephoneTwo(txlUsertemp.getTelephoneTwo());
                	txlUserService.update(txlUser);
                }
			}
    	}
      	
    }
	
	/**
	 * 同步通讯录
	 * 
	 * */
	public void SyncTxlUser() {

		List<TxlUser> userList1 = txlUserService.queryListByOrganId("root");
		for(TxlUser txlUser : userList1) {
			txlUser.setOrderid(String.valueOf(num));
			txlUserService.update(txlUser);
			num += 1;
		}
		
		//查询出root节点下的机构
		List<TxlOrgan> list = txlOrganService.queryRoot();
		for(int i = 0;i<list.size();i++) {
			List<TxlUser> userList = txlUserService.queryListByOrganId(list.get(i).getOrganid());
			for(TxlUser txlUser : userList) {
				txlUser.setOrderid(String.valueOf(num));
				txlUserService.update(txlUser);
				num += 1;
			}
			List<TxlOrgan> list2 = txlOrganService.queryListByOrganId(list.get(i).getOrganid());
			if(list2 != null && list2.size()>0) {
				for(int j = 0;j<list2.size();j++) {
					List<TxlUser> userList2 = txlUserService.queryListByOrganId(list2.get(j).getOrganid());
					for(TxlUser txlUser : userList2) {
						txlUser.setOrderid(String.valueOf(num));
						txlUserService.update(txlUser);
						num += 1;
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	

}