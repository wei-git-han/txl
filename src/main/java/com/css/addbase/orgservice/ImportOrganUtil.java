package com.css.addbase.orgservice;
import java.util.Timer;
import java.util.TimerTask;

import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.entity.BaseAppUser;
import com.css.apporgan.service.BaseAppOrganService;
import com.css.apporgan.service.BaseAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.base.utils.Response;
import com.css.txl.entity.TxlOrgan;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;

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

	@Autowired
	private BaseAppUserService baseAppUserService;

	@Autowired
	private BaseAppOrganService baseAppOrganService;
	
	
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
			baseAppUserService.clearUser();
			baseAppOrganService.clearOrgan();
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
			System.out.println(e.getMessage());
			
		}
	}
	
	/**
	 * 导入系统部门及其所属子部门和人员
	 */
	public void importOrg(String organId){
		Organ organ = orgService.getOrgan(organId);
		TxlOrgan baseAppOrgantemp = txlOrganService.queryObject(organId);
		BaseAppOrgan baseOrgantmp = baseAppOrganService.queryObject(organId);
		TxlOrgan txlOrgan = new TxlOrgan();
		BaseAppOrgan baseAppOrgantmp = new BaseAppOrgan();
		txlOrgan.setCode(organ.getCode());
		txlOrgan.setIsdelete(String.valueOf(organ.getIsDelete()));
		if(organ.getOrderId()!=null){
			txlOrgan.setOrderid(String.valueOf(organ.getOrderId()));
			baseAppOrgantmp.setSort(organ.getOrderId());
		}
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
		//插入base_app_organ表
		baseAppOrgantmp.setId(organ.getOrganId());
		baseAppOrgantmp.setName(organ.getOrganName());
		baseAppOrgantmp.setParentId(organ.getFatherId());
		baseAppOrgantmp.setTreePath(organ.getP());
		baseAppOrgantmp.setIsdelete(organ.getIsDelete());
		if(baseOrgantmp!=null){
			baseAppOrganService.update(baseAppOrgantmp);
		}else {
			baseAppOrganService.save(baseAppOrgantmp);
		}
		System.out.println(organ.getOrganId()+":"+organ.getOrganName()+"导入成功！");
		Organ[] organs = orgService.getSubOrg(organId);
		for (Organ sysOrgan:organs) {
			TxlOrgan appOrgantemp = txlOrganService.queryObject(organId);
			BaseAppOrgan baseOrgantemp = baseAppOrganService.queryObject(organId);
			TxlOrgan txlorgan = new TxlOrgan();
			BaseAppOrgan baseAppOrgan = new BaseAppOrgan();
			txlorgan.setCode(organ.getCode());
			txlorgan.setIsdelete(String.valueOf(organ.getIsDelete()));
			if(organ.getOrderId()!=null){
				txlorgan.setOrderid(String.valueOf(organ.getOrderId()));
				baseAppOrgan.setSort(organ.getOrderId());
			}
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
			//插入base_app_organ表
			baseAppOrgan.setId(organ.getOrganId());
			baseAppOrgan.setName(organ.getOrganName());
			baseAppOrgan.setParentId(organ.getFatherId());
			baseAppOrgan.setTreePath(organ.getP());
			baseAppOrgan.setIsdelete(organ.getIsDelete());
			if(baseOrgantemp != null){
				baseAppOrganService.update(baseAppOrgan);
			}else {
				baseAppOrganService.save(baseAppOrgan);
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
			txlUser.setOrderid(String.valueOf(userInfo.getRelations().get(0).get("orderId")));
			txlUser.setAccount(userInfo.getAccount());
			txlUser.setDept(userInfo.getDept());
			if(null != userInfo.getFullname() && !"".equals(userInfo.getFullname())) {
				txlUser.setFullname(userInfo.getFullname());
				txlUser.setPyName(userInfo.getFullname().replaceAll("首长","SZ").replaceAll("处长","CZ").replaceAll("局长","JZ"));
				}
			txlUser.setOrganid(userInfo.getRelations().get(0).get("organId"));
			txlUser.setPassword(userInfo.getPassword());
			txlUser.setSeclevel(userInfo.getSecLevel());
			txlUser.setSex(userInfo.getSex());
			txlUser.setUseremail(userInfo.getUserEmail());
			txlUser.setUserid(userInfo.getUserid());
			if(baseAppUsertemp!=null){
				//通讯录中职位不从单点中取，完全自己编辑修改
				/*if (StringUtils.isEmpty(baseAppUsertemp.getPost())) {
					txlUser.setPost((StringUtils.isNotBlank(userInfo.getDuty())&&(userInfo.getDuty().indexOf(";")!=-1))? userInfo.getDuty().split(";")[1]:"");
				}*/
				txlUser.setPost(baseAppUsertemp.getPost());
				txlUser.setTelephone(userInfo.getTel());
				txlUser.setMobile(userInfo.getMobile());
				txlUser.setMobileTwo(baseAppUsertemp.getMobileTwo());
				txlUser.setTelephoneTwo(baseAppUsertemp.getTelephoneTwo());
				txlUserService.update(txlUser);
			}else{
				//txlUser.setPost((StringUtils.isNotBlank(userInfo.getDuty())&&(userInfo.getDuty().indexOf(";")!=-1))? userInfo.getDuty().split(";")[1]:"");
				txlUser.setTelephone(userInfo.getTel());
            	txlUser.setMobile(userInfo.getMobile());
				txlUserService.save(txlUser);
			}
			//插入base_app_user表
			BaseAppUser baseAppUser = new BaseAppUser();
			baseAppUser.setId(userInfo.getUserid());
			baseAppUser.setUserId(userInfo.getUserid());
			baseAppUser.setTruename(userInfo.getFullname());
			baseAppUser.setAccount(userInfo.getAccount());
			baseAppUser.setSex(userInfo.getSex());
			baseAppUser.setUseremail(userInfo.getUserEmail());
			baseAppUser.setSeclevel(userInfo.getSecLevel());
			baseAppUser.setOrganid(userInfo.getRelations().get(0).get("organId"));
			baseAppUser.setSort(userInfo.getOrderId());
			baseAppUser.setIsdelete(userInfo.getIsDelete());
			baseAppUser.setMobile(userInfo.getMobile());
			baseAppUser.setTelephone(userInfo.getTel());
			BaseAppUser baseUsertemp = baseAppUserService.queryObject(userInfo.getUserid());
			if(baseUsertemp == null){
				baseAppUserService.save(baseAppUser);
			}else {
				baseAppUserService.update(baseAppUser);
			}

			System.out.println(userInfo.getUserid()+":"+userInfo.getFullname()+"导入成功！");
			txlUser = null;
		}
	}

}