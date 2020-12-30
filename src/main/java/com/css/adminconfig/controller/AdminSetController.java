package com.css.adminconfig.controller;

import com.alibaba.fastjson.JSONObject;
import com.css.adminconfig.entity.AdminSet;
import com.css.adminconfig.entity.BaseTreeObject;
import com.css.adminconfig.service.AdminSetService;
import com.css.appconfig.entity.BaseAppConfig;
import com.css.appconfig.service.BaseAppConfigService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.css.txl.controller.TxlUserController;
import com.css.txl.entity.TxlUser;
import com.css.txl.service.TxlOrganService;
import com.css.txl.service.TxlUserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/txl/adminconfig")
public class AdminSetController {
    @Autowired
    private AdminSetService adminSetService;
    @Autowired
    private BaseAppConfigService baseAppConfigService;

    @Autowired
    private TxlOrganService txlOrganService;

    @Autowired
    private TxlUserService txlUserService;

	private final Logger logger = LoggerFactory.getLogger(AdminSetController.class);
    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public void list(Integer page, Integer pagesize ,String adminType){
        PageHelper.startPage(page, pagesize);
        Map<String, Object > map = new HashMap<>();
        map.put("adminType", adminType);
        List<AdminSet> adminSetList = adminSetService.queryList(map);
        GwPageUtils pageUtil = new GwPageUtils(adminSetList);
        Response.json(pageUtil);
    }

    /**
     * 局管理员列表
     */
    @ResponseBody
    @RequestMapping("/juList")
    public void juList(Integer page, Integer pagesize ,String adminType){
        List<AdminSet> adminSetList=null;
        String loginUserId = CurrentUser.getUserId();
        //获取当前人的管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
        String adminFlag = adminSetService.getAdminTypeByUserId(loginUserId);
        PageHelper.startPage(page, pagesize);
        if(StringUtils.equals("2", adminFlag) || StringUtils.equals("3", adminFlag)) {
            adminSetList=adminSetService.queryJuAdminList(loginUserId);
            //判断即是部管理员又是局管理员
            if(StringUtils.equals("3", adminFlag)){
                for (AdminSet adminSet : adminSetList) {
                    adminSet.setEditFlag("1");
                }
            }
        }else {
            Map<String, Object > map = new HashMap<>();
            map.put("adminType", adminType);
            adminSetList = adminSetService.queryList(map);
            for (AdminSet adminSet : adminSetList) {
                adminSet.setEditFlag("1");
            }
        }
        GwPageUtils pageUtil = new GwPageUtils(adminSetList);
        Response.json(pageUtil);
    }

    /**
     * 	获取某人的管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
     */
    @ResponseBody
    @RequestMapping("/getAuthor")
    public void getAuthor(){
        String adminFlag = adminSetService.getAdminTypeByUserId(CurrentUser.getUserId());
        if(StringUtils.isBlank(adminFlag)){
            adminFlag = "没有查到该用户信息";
        }
        Response.json(adminFlag);
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info")
    public void info(String id){
        AdminSet adminSet = adminSetService.queryObject(id);
        Response.json(adminSet);
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public void save(AdminSet adminSet){
        String deptId="";
        String deptName="";
        String orgName="";
        String orgId="";
        String userId = adminSet.getUserId();
        JSONObject json = new JSONObject();
        if(StringUtils.isNotBlank(userId)) {

            orgId= txlOrganService.getBarOrgIdByUserId(userId);
            orgName=txlOrganService.queryObject(orgId).getOrganname();
            deptName=orgName;
        }
        if(StringUtils.isNotBlank(adminSet.getId())) {
            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("adminType", adminSet.getAdminType());
            adminMap.put("userId", adminSet.getUserId());
            List<AdminSet> queryList = adminSetService.queryList(adminMap);
            if(queryList != null && queryList.size()>0) {
                json.put("result", "exist");
                Response.json(json);
                return;
            }else {
                adminSet.setDeptId(deptId);
                adminSet.setDeptName(deptName);
                adminSet.setOrgId(orgId);
                adminSet.setOrgName(orgName);
                adminSet.setOrgName(orgName);
                adminSetService.update(adminSet);
            }
        }else {
            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("adminType", adminSet.getAdminType());
            adminMap.put("userId", adminSet.getUserId());
            List<AdminSet> queryList = adminSetService.queryList(adminMap);
            if(queryList != null && queryList.size()>0) {
                json.put("result", "exist");
                Response.json(json);
                return;
            }else {
                adminSet.setId(UUIDUtils.random());
                adminSet.setDeptId(deptId);
                adminSet.setDeptName(deptName);
                adminSet.setOrgId(orgId);
                adminSet.setOrgName(orgName);
                adminSetService.save(adminSet);
            }
        }
        json.put("result", "success");
        String userIdtemp = CurrentUser.getUserId();
        Map<String, Object > map = new HashMap<>();
        map.put("userId",userIdtemp);
        List<AdminSet> adminSets = adminSetService.queryList(map);
        if(adminSets!=null && adminSets.size()>0){
            json.put("isShow", true);
            if(adminSets.size()==2){
                json.put("adminTypetemp","3");
            }else {
                AdminSet adminSettemp = adminSets.get(0);
                if(adminSettemp.getAdminType().equals("2")){
                    json.put("adminTypetemp","2");
                }else if(adminSettemp.getAdminType().equals("1")){
                    json.put("adminTypetemp","1");
                }
            }
        }else {
            json.put("isShow", false);
        }
        Response.json(json);

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    public void delete(String ids){
        JSONObject json = new JSONObject();
        String[] idArry = ids.split(",");
        adminSetService.deleteBatch(idArry);
        json.put("result","success");
        String userIdtemp = CurrentUser.getUserId();
        Map<String, Object > map = new HashMap<>();
        map.put("userId",userIdtemp);
        List<AdminSet> adminSets = adminSetService.queryList(map);
        if(adminSets!=null && adminSets.size()>0){
            json.put("isShow", true);
            if(adminSets.size()==2){
                json.put("adminTypetemp","3");
            }else {
                AdminSet adminSettemp = adminSets.get(0);
                if(adminSettemp.getAdminType().equals("2")){
                    json.put("adminTypetemp","2");
                }else if(adminSettemp.getAdminType().equals("1")){
                    json.put("adminTypetemp","1");
                }
            }
        }else {
            json.put("isShow", false);
        }
    	Date date = new Date();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
		logger.info("当前删除操作人："+CurrentUser.getSSOUser().getFullname()+"---id:"+CurrentUser.getUserId()+"--时间是："+format);
        Response.json(json);
    }
}
