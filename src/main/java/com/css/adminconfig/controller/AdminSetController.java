package com.css.adminconfig.controller;

import com.css.adminconfig.service.AdminSetService;
import com.css.base.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/txl/adminconfig")
public class AdminSetController {
    @Autowired
    private AdminSetService adminSetService;

    public void getAuthor(){

        String adminFlag = adminSetService.getAdminTypeByUserId(CurrentUser.getUserId());
    }

}
