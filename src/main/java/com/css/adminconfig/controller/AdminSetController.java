package com.css.adminconfig.controller;

import com.css.adminconfig.entity.AdminSet;
import com.css.adminconfig.entity.BaseTreeObject;
import com.css.adminconfig.service.AdminSetService;
import com.css.appconfig.entity.BaseAppConfig;
import com.css.appconfig.service.BaseAppConfigService;
import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.entity.BaseAppUser;
import com.css.apporgan.service.BaseAppOrganService;
import com.css.apporgan.service.BaseAppUserService;
import com.css.apporgmapped.constant.AppConstant;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/txl/adminconfig")
public class AdminSetController {
    @Autowired
    private AdminSetService adminSetService;
    @Autowired
    private BaseAppOrganService baseAppOrganService;
    @Autowired
    private BaseAppUserService baseAppUserService;
    @Autowired
    private BaseAppConfigService baseAppConfigService;

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
        PageHelper.startPage(1, pagesize);
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
        if(StringUtils.isNotBlank(userId)) {
            orgId = baseAppUserService.getBareauByUserId(userId);
            if(StringUtils.isNotBlank(orgId)) {
                BaseAppOrgan organ = baseAppOrganService.queryObject(orgId);
                if(organ != null) {
                    orgName=organ.getName();
                }
            }
            List<BaseAppUser> users = baseAppUserService.findByUserId(userId);
            if(users != null) {
                deptId = users.get(0).getOrganid();
                BaseAppOrgan dept = baseAppOrganService.queryObject(deptId);
                if(dept != null) {
                    deptName=dept.getName();
                }
            }
        }
        if(StringUtils.isNotBlank(adminSet.getId())) {
            adminSet.setDeptId(deptId);
            adminSet.setDeptName(deptName);
            adminSet.setOrgId(orgId);
            adminSet.setOrgName(orgName);
            adminSet.setOrgName(orgName);
            adminSetService.update(adminSet);
        }else {
            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("adminType", adminSet.getAdminType());
            adminMap.put("userId", adminSet.getUserId());
            List<AdminSet> queryList = adminSetService.queryList(adminMap);
            if(queryList != null && queryList.size()>0) {
                Response.json("result", "exist");
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
        Response.json("result", "success");
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    public void delete(String ids){
        String[] idArry = ids.split(",");
        adminSetService.deleteBatch(idArry);
        Response.json("result","success");
    }

    /**
     * 查询所有首长
     */
    @ResponseBody
    @RequestMapping("/allShouZhang")
    public void allShouZhang() {
        BaseAppConfig mapped = baseAppConfigService.queryObject(AppConstant.LEAD_TEAM);//首长单位id
        Map<String, Object> map = new HashMap<>();
        map.put("organid",mapped.getValue());
        List<BaseAppUser> baseAppUsers = baseAppUserService.queryList(map);
        List<BaseTreeObject> baseTreeObjects = new ArrayList<BaseTreeObject>();
        BaseTreeObject baseTreeObject = null;
        for (BaseAppUser baseAppUser : baseAppUsers) {
            baseTreeObject = new BaseTreeObject();
            baseTreeObject.setId(baseAppUser.getUserId());
            baseTreeObject.setText(baseAppUser.getTruename());
            baseTreeObject.setType("1");
            baseTreeObjects.add(baseTreeObject);
        }
        Response.json(baseTreeObjects);
    }

}
