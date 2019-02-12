/*
package com.jones.mars.util;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.User;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class LoginUtil_bak {
    public static final String CUR_USER = "authorization";
    public static final int SESSION_MAX_INACTIVE_INTERVAL = 86400;
    public static LoginUtil_bak INSTANCE = null;
    private  ConcurrentHashMap<String, User> loginUser = new ConcurrentHashMap<>();

    public static LoginUtil_bak getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginUtil_bak.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginUtil_bak();
                }
            }
        }
        return INSTANCE;
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }

    private HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getResponse();
    }

    private HttpSession getSession() {
        HttpSession session = getRequest().getSession(true);
        session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
        return session;
    }

    public User getUser() {
        System.out.println(JSONObject.toJSONString(getRequest().getCookies()));
        String auth = getRequest().getHeader(CUR_USER);
        if(StringUtils.isEmpty(auth)){
            Cookie[] cookies = getRequest().getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(CUR_USER)){
                    auth = cookie.getValue();
                    break;
                }
            }
        }
        if(StringUtils.isEmpty(auth)){
            return null;
        }
        return loginUser.get(auth.replace(" ", ""));
//        return (User) getSession().getAttribute(CUR_USER);
    }

    public void removeUser() {
        Cookie cookie = new Cookie(CUR_USER, "");
        cookie.setMaxAge(0);
        getResponse().addCookie(cookie);
        String auth = getRequest().getHeader(CUR_USER);
        if(!StringUtils.isEmpty(auth)){
            loginUser.remove(auth.replace(" ", ""));
        }
        getSession().removeAttribute(CUR_USER);
    }

    public void setUser(String authorization, User user) {
        authorization = authorization.replace(" ", "");
        Cookie cookie = new Cookie(CUR_USER, authorization);
        cookie.setMaxAge(SESSION_MAX_INACTIVE_INTERVAL);
        cookie.setDomain("chikai-tech.cn");
        cookie.setPath("/");
        getResponse().addCookie(cookie);
        user.setAuth(authorization);
        loginUser.put(authorization, user);
        getSession().setAttribute(CUR_USER, authorization);
    }

    public boolean existsAuthorization(String authorization){
        return loginUser.containsKey(authorization.replace(" ", ""));
//        User user = (User) getSession().getAttribute(CUR_USER);
//        return user == null ? null : user.getAuth();
    }
}

*/
