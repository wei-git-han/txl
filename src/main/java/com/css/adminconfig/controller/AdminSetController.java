package com.css.adminconfig.controller;

import com.css.adminconfig.entity.AdminSet;
import com.css.adminconfig.service.AdminSetService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/txl/adminconfig")
public class AdminSetController {
    @Autowired
    private AdminSetService adminSetService;

    /**
     * 	获取某人的管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
     */
    @ResponseBody
    @RequestMapping("/getAuthor")
    public void getAuthor(){
        String adminFlag = adminSetService.getAdminTypeByUserId(CurrentUser.getUserId());
        Response.json(adminFlag);
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

}
