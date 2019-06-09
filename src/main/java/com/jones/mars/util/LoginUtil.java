package com.jones.mars.util;

import com.jones.mars.model.User;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class LoginUtil {
    public static final String AUTH = "authorization";
    public static final int SESSION_MAX_INACTIVE_INTERVAL = 86400;
    public static LoginUtil INSTANCE = null;
    private  ConcurrentHashMap<String, User> loginUser = new ConcurrentHashMap<>();

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

    public static String getLoginSgname(){
        return getInstance().getUser().getSgname();
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
        String auth = getRequest().getHeader(AUTH);
        if(StringUtils.isEmpty(auth)){
            Cookie[] cookies = getRequest().getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(AUTH)){
                        auth = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if(StringUtils.isEmpty(auth)){
            return User.builder().sgname("常永全").userType(User.COMMON).id(11).build();
        }
        return getLoginUser(auth);
    }

    public void removeUser() {
        Cookie cookie = new Cookie(AUTH, "");
        cookie.setMaxAge(0);
        getResponse().addCookie(cookie);
        String auth = getRequest().getHeader(AUTH);
        if(!StringUtils.isEmpty(auth)){
            loginUser.remove(auth.replace(" ", ""));
            getSession().removeAttribute(AUTH);
        }
    }

    public void setUser(String authorization, User user) {
        authorization = authorization.replace(" ", "") + user.getId();
        System.out.printf("set authorization:" + authorization);
        getResponse().addHeader("Set-cookie", "authorization=" + authorization + ";domain=.chikai-tech.cn;path=/");
        setLoginUser(authorization, user);
    }

    private User getLoginUser(String authorization){
        authorization = authorization.replace(" ", "");
        System.out.println("auth:" + authorization + "; userId: " + authorization.substring(37));
        User user = loginUser.get(authorization.substring(37));
        if(user != null){
            return user;
        } else {
            return null;
        }
    }

    private void setLoginUser(String authorization, User user){
        user.setAuth(authorization);
        loginUser.put(authorization.substring(37), user);
    }

}
