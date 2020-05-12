package com.css.adminconfig.service;

public interface AdminSetService {

    //获取某人的管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
    String getAdminTypeByUserId(String userId);
}
