package com.jones.mars.util;

import com.jones.mars.constant.ApplicationConst;
import com.jones.mars.model.User;
import com.jones.mars.model.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginUtil {
    public static final String APP_AUTH = "authorization";
    public static final String USER_MOBILE = "mobile";
    public static final String USER_ID = "userId";
    public static String APP_DOMAIN = ApplicationConst.APP_DOMAIN;
    public static final int COOKIE_MAX_INACTIVE_INTERVAL = 86400;
    public static LoginUtil INSTANCE = null;
    private ConcurrentHashMap<String, User> loginUser = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, LinkedList<String>> userIdAuth = new ConcurrentHashMap<>();

//    @Value("${app.domain:vr2shipping.com}")
//    private void setAppDomain(String appDomain){
//        LoginUtil.APP_DOMAIN = appDomain;
//    }

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

    public boolean existsAuth(String auth){
        return loginUser.containsKey(auth);
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
        session.setMaxInactiveInterval(COOKIE_MAX_INACTIVE_INTERVAL);
        return session;
    }

    public User getUser() {
        if(getRequest().getCookies() != null) {
            Optional<Cookie> cookieAuth = Arrays.asList(getRequest().getCookies()).stream().filter(p -> p.getName().equals(APP_AUTH)).findAny();
            if (cookieAuth.isPresent()) {
                String auth = cookieAuth.get().getValue();
                return getLoginUser(auth);
            }
        }
        return null;
    }

    public void removeUser() {
        Cookie cookie = new Cookie(APP_AUTH, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(APP_DOMAIN);
        getResponse().addCookie(cookie);
        Optional<Cookie> cookieAuth = Arrays.asList(getRequest().getCookies()).stream().filter(p->p.getName().equals(APP_AUTH)).findAny();
        if(cookieAuth.isPresent()){
            loginUser.remove(cookieAuth.get().getValue());
        }
    }

    public void setUser(String authorization, User user) {
        authorization = authorization.replace(" ", "") + user.getId();
        System.out.printf("set authorization:" + authorization);
        Cookie cookie =new Cookie(APP_AUTH, authorization);
        cookie.setPath("/");
        cookie.setMaxAge(3600*24*1000);
        cookie.setDomain(APP_DOMAIN);
        Cookie mobileCookie =new Cookie(USER_MOBILE, user.getMobile());
        mobileCookie.setPath("/");
        mobileCookie.setMaxAge(3600*24*1000);
        mobileCookie.setDomain(APP_DOMAIN);
        Cookie userIdCookie =new Cookie(USER_ID, user.getId().toString());
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(3600*24*1000);
        userIdCookie.setDomain(APP_DOMAIN);
        getResponse().addCookie(cookie);
        getResponse().addCookie(mobileCookie);
        getResponse().addCookie(userIdCookie);
//        getResponse().addHeader("Set-cookie", "authorization=" + authorization + ";domain=vr2shipping.com;path=/");
        setLoginUser(authorization, user);
    }

    public User refreshLoginUser(String authorization){
        User user = getLoginUser(authorization);
        if(user != null){
            setLoginUser(authorization, user);
        }
        return user;
    }

    private User getLoginUser(String authorization){
        User user = loginUser.get(authorization);
        if(user != null){
            System.out.println("auth:" + authorization + "; userId: " + authorization.substring(37));
            return user;
        } else {
            System.out.println("cannot find login user! auth:" + authorization);
            return null;
        }
    }

    synchronized void setLoginUser(String authorization, User user){
        LinkedList<String> authList = userIdAuth.getOrDefault(user.getId(), new LinkedList<>());
        authList.remove(authorization);
        if(authList.size()>=user.getTerminalLimit()){
            String oldAuth = authList.poll();
            loginUser.remove(oldAuth);
        }
        authList.offer(authorization);
        userIdAuth.put(user.getId(), authList);
        user.setAuth(authorization);
        loginUser.put(authorization, user);
    }

}
