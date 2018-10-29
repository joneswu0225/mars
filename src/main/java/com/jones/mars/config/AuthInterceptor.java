package com.jones.mars.config;

import com.jones.mars.model.param.UserLoginParam;
import com.jones.mars.service.UserService;
import com.jones.mars.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Slf4j
@Configuration
public class AuthInterceptor extends WebMvcConfigurerAdapter {

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

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
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/user/auth**");
        addInterceptor.excludePathPatterns("/logout**");
        addInterceptor.excludePathPatterns("/pano**");
        addInterceptor.excludePathPatterns("/home/**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            HttpSession session = request.getSession();
            String url = request.getRequestURI();
            if (request.getQueryString() != null) {
                url += "?" + request.getQueryString();
            }
            if (session.getAttribute(LoginUtil.CUR_USER) == null) {
                UserLoginParam user = new UserLoginParam();
                user.setMobile("18616701071");
                user.setPassword("123456789");
                service.doLogin(user);
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