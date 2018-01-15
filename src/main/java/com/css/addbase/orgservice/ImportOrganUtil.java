package com.css.addbase.orgservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
@RequestMapping("/org")
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
					List<TxlOrgan> baseAppOrgans = txlOrganService.queryList(null);
					if (baseAppOrgans == null || baseAppOrgans.size() == 0) {
						importOrg("root");
						System.out.println("首次组织机构导入成功！");
					} else {
						System.out.println("组织机构内已存在数据！");
					}
				} catch (Exception e) {
					System.out.println("首次组织机构导入成功！");
					System.out.println(e);
				}
			}
		}, 60000);
	}
	
	/**
	 * 手动导入组织机构路径
	 */
	@ResponseBody
	@RequestMapping("/import.htm")
	public void importOrgan() {
		try {
			txlOrganService.clearOrgan();
			txlUserService.clearUser();
			importOrg("root");
			System.out.println("首次组织机构导入成功！");
			Response.json("织机构导入成功！");
		} catch (Exception e) {
			System.out.println("首次组织机构导入成功！");
			System.out.println(e);
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
