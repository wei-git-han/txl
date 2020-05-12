package com.css.adminconfig.service.Impl;

import com.css.adminconfig.dao.AdminSetDao;
import com.css.adminconfig.entity.AdminSet;
import com.css.adminconfig.service.AdminSetService;
import com.css.base.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adminSetService")
public class AdminSetServiceImpl implements AdminSetService {
    @Autowired
    private AdminSetDao adminSetDao;

    @Value("${csse.txl.appId}")
    private  String appId;

    @Value("${csse.txl.appSecret}")
    private  String clientSecret;

    @Override
    public String getAdminTypeByUserId(String userId) {
        //管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
        String adminFlag = "";
        boolean admin = CurrentUser.getIsManager(appId, clientSecret);
        if(admin) {
            adminFlag="0";
        }else {
            //当前登录人的管理员类型
            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("userId",userId);
            List<AdminSet> adminList = adminSetDao.queryList(adminMap);
            if(adminList != null && adminList.size()>0) {
                if(adminList.size()==1) {
                    String adminType = adminList.get(0).getAdminType();
                    adminFlag=adminType;
                }if(adminList.size()>1) {
                    adminFlag="3";
                }
            }
        }
        return adminFlag;
    }

    @Override
    public List<AdminSet> queryJuAdminList(String userId) {
        return adminSetDao.queryJuAdminList(userId);
    }

    @Override
    public List<AdminSet> queryList(Map<String, Object> map){
        return adminSetDao.queryList(map);
    }

}
