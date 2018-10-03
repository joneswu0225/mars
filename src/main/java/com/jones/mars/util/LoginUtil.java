package com.jones.mars.util;

import com.jones.mars.model.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {
    public static final String CUR_USER = "authorization";
    public static final int SESSION_MAX_INACTIVE_INTERVAL = 86400;
    public static LoginUtil INSTANCE = null;

    public static LoginUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginUtil();
                }
            }
        }
        return INSTANCE;
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }

    private HttpSession getSession() {
        HttpSession session = getRequest().getSession(true);
        session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
        return session;
    }

    public User getUser() {
        return (User) getSession().getAttribute(CUR_USER);
    }

    public void removeUser() {
        getSession().removeAttribute(CUR_USER);
    }

    public void setUser(User user) {
        getSession().setAttribute(CUR_USER, user);
    }
}

