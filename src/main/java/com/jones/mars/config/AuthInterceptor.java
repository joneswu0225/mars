package com.jones.mars.config;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.param.UserLoginParam;
import com.jones.mars.service.UserService;
import com.jones.mars.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;

@Slf4j
@Configuration
public class AuthInterceptor extends WebMvcConfigurerAdapter {

    @Autowired
    private UserService service;

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/regist");
        addInterceptor.excludePathPatterns("/user/verifyCode");
        addInterceptor.excludePathPatterns("/user/**/exists");
        addInterceptor.excludePathPatterns("/logout**");
        addInterceptor.excludePathPatterns("/pano**");
        addInterceptor.excludePathPatterns("/home/**");
        addInterceptor.excludePathPatterns("/enterpriseShown**");
        addInterceptor.excludePathPatterns("/swagger-ui.html");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                System.out.println(name + " : " + request.getHeader(name));
            }
            String url = request.getRequestURI();
            if (request.getQueryString() != null) {
                url += "?" + request.getQueryString();
            }
            log.info("authorization in request header is :" + request.getHeader("Authorization"));
            if (LoginUtil.getInstance().getUser() == null) {
                log.info("request address: " + request.getRemoteAddr());
                if("127.0.0.1".equals(request.getRemoteAddr())){
                    log.info("当前请求为内部接口请求，且无登录状态，设置默认用户为：13564332436");
                    UserLoginParam user = new UserLoginParam();
                    user.setMobile("13564332436");
                    user.setPassword("12345678");
                    service.doLogin(user);
                } else {
                    log.info("当前用户未登陆");
                    response.sendError(HttpStatus.FORBIDDEN.value(), "请登录后进行操作");
                    return false;
                }
            } else {
                log.info("当前访问： {}, 用户：{}", url, LoginUtil.getInstance().getUser().getMobile());
            }

            //跳转登录
//            response.sendRedirect("/login" + ((url.contains("/error")) ? "" : ("?callback=" + url)));
//            return false;
            // has login, handle permission
            if (hasPermission(handler)) {
                return true;
            } else {
                response.sendError(HttpStatus.FORBIDDEN.value(), "没有权限");
                log.warn("User {} are trying to access {}, but (s)he has no permission", LoginUtil.getInstance().getUser().getMobile(), url);
                return false;
            }
        }
    }

    private boolean hasPermission(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginUser loginUser = handlerMethod.getMethod().getAnnotation(LoginUser.class);
            // if RequirePermission Not found before method, check class level
            if (loginUser == null) {
                loginUser = handlerMethod.getMethod().getDeclaringClass().getAnnotation(LoginUser.class);
            }
            if (loginUser != null) {
                return Arrays.asList(loginUser.role()).contains(LoginUtil.getInstance().getUser().getUserType());
            }
        }
        return true;
    }
}