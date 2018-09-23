package com.jones.mars.controller;

import com.jones.mars.model.User;
import com.jones.mars.util.LoginUtil;

public class BaseController {
    protected int size = 20;
    protected int page = 1;

    protected User getLoginUser() {
        return LoginUtil.getInstance().getUser();
    }

    protected void setLoginUser(User user) {
        LoginUtil.getInstance().setUser(user);
    }

    protected void removeLoginUser() {
        LoginUtil.getInstance().removeUser();
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
