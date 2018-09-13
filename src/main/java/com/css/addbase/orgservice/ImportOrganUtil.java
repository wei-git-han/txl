package com.css.addbase.orgservice;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.base.utils.Response;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;
import com.css.txl.utils.ChineseFCUtil;

/**
 * 组织机构导入接口
 * @author gengds
 */
@Component
@RequestMapping("/app/org")
public class ImportOrganUtil {
	
	@Autowired
	private TxlOrganService txlOrganService;
	
	@Autowired
	private TxlUserService txlUserService;
	
	@Autowired
	private OrgService orgService;
	
	
	public ImportOrganUtil() {
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				try {
					importOrg("root");
					System.out.println("组织机构导入成功！");
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}, 60000);
	}
	
	/**
	 * 清空组织机构
	 */
	@ResponseBody
	@RequestMapping("/delete.htm")
	public void deleteOrgan() {
		try {
			txlOrganService.clearOrgan();
			txlUserService.clearUser();
			Response.json("清空组织结构成功");
		} catch (Exception e) {
			Response.json(e);
		}
	}
	/**
	 * 导入组织机构
	 */
	@ResponseBody
	@RequestMapping("/import.htm")
	public void importOrgan() {
		try {
			importOrg("root");
			Response.json("更新组织机构成功！");
		} catch (Exception e) {
			Response.json(e);
		}
	}
	
	/**
	 * 导入系统部门及其所属子部门和人员
	 */
	public void importOrg(String organId){
		Organ organ = orgService.getOrgan(organId);
		TxlOrgan baseAppOrgantemp = txlOrganService.queryObject(organId);
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
		if(baseAppOrgantemp!=null){
			txlOrganService.update(txlOrgan);
		}else{
			txlOrganService.save(txlOrgan);
		}
		System.out.println(organ.getOrganId()+":"+organ.getOrganName()+"导入成功！");
		Organ[] organs = orgService.getSubOrg(organId);
		for (Organ sysOrgan:organs) {
			TxlOrgan appOrgantemp = txlOrganService.queryObject(organId);
			TxlOrgan txlorgan = new TxlOrgan();
			txlorgan.setCode(organ.getCode());
			txlorgan.setIsdelete(String.valueOf(organ.getIsDelete()));
			txlorgan.setOrderid(String.valueOf(organ.getOrderId()));
			txlorgan.setDn(organ.getDn());
			txlorgan.setFatherid(organ.getFatherId());
			txlorgan.setOrganid(organ.getOrganId());
			txlorgan.setOrganname(organ.getOrganName());
			txlorgan.setOrguuid(organ.getOrguuid());
			txlorgan.setType(organ.getType());
			txlorgan.setTimestamp(organ.getTimestamp());
			if(appOrgantemp!=null){
				txlOrganService.update(txlorgan);
			}else{
				txlOrganService.save(txlorgan);
			}
			System.out.println(organ.getOrganId()+":"+organ.getOrganName()+"导入成功！");
			txlorgan = null;
			importOrg(sysOrgan.getOrganId());
		}
		UserInfo[] userInfos = orgService.getUserInfos(organId);
		for (UserInfo userInfo:userInfos) {
			TxlUser baseAppUsertemp = txlUserService.queryObject(userInfo.getUserid());
			TxlUser txlUser=new TxlUser();
			txlUser.setIsdelete(String.valueOf(userInfo.getIsDelete()));
			txlUser.setOrderid(String.valueOf(userInfo.getOrderId()));
			txlUser.setAccount(userInfo.getAccount());
			txlUser.setDept(userInfo.getDept());
			txlUser.setPost((StringUtils.isNotBlank(userInfo.getDuty())&&(userInfo.getDuty().indexOf(";")!=-1))? userInfo.getDuty().split(";")[1]:"");
//			txlUser.setTelephone(userInfo.getTel());
			if(null != userInfo.getFullname() && !"".equals(userInfo.getFullname())) {
				txlUser.setFullname(userInfo.getFullname());
				txlUser.setPyName(userInfo.getFullname().replaceAll("首长","SZ").replaceAll("处长","CZ").replaceAll("局长","JZ"));
				}
//			txlUser.setMobile(userInfo.getMobile());
			txlUser.setOrganid(userInfo.getOrganId());
			txlUser.setPassword(userInfo.getPassword());
			txlUser.setSeclevel(userInfo.getSecLevel());
			txlUser.setSex(userInfo.getSex());
			txlUser.setUseremail(userInfo.getUserEmail());
			txlUser.setUserid(userInfo.getUserid());
			if(baseAppUsertemp!=null){
				txlUserService.update(txlUser);
			}else{
				txlUserService.save(txlUser);
			}
			System.out.println(userInfo.getUserid()+":"+userInfo.getFullname()+"导入成功！");
			txlUser = null;
		}
	}

}
