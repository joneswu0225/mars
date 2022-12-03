package com.jones.mars.controller;

import com.jones.mars.exception.NeedLoginException;
import com.jones.mars.model.User;
import com.jones.mars.util.LoginUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {
    protected int size = 20;
    protected int page = 1;

    protected User getLoginUser() {
        User loginUser =  LoginUtil.getInstance().getUser();
        if(loginUser == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();
            try {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "请登录后进行操作");
            } catch (IOException e) {
                throw(new NeedLoginException("无法获取登录用户"));
            }
        }
        return loginUser;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
