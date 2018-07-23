package com.jones.mars.controller;

import com.jones.mars.model.GeneralResponse;
 import com.jones.mars.model.User;
 import com.jones.mars.service.UserService;
 import com.jones.mars.util.Page;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping({"/user"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String list(@RequestParam(value = "size", required = false, defaultValue = "20") int size, @RequestParam(value = "page", required = false, defaultValue = "0") int page, ModelMap map) {
        Page userList = this.userService.findByPage(getQuery(page, size));
        map.put("page", userList);
        return "user/list";

    }

    @ResponseBody

    @RequestMapping(value = {"/auth"}, method = {RequestMethod.POST})
    GeneralResponse auth(User user) {
        GeneralResponse resp = new GeneralResponse(false, "用户名或密码错误!");

        User loginUser = user;
        if (loginUser != null) {
            setLoginUser(loginUser);
            resp = new GeneralResponse(true, "success");

        }
        return resp;
    }


    @ResponseBody

    @RequestMapping(value = {"/logout"}, method = {RequestMethod.POST})
    GeneralResponse logout() {
        removeLoginUser();
        return new GeneralResponse(true, "success");

    }

}

