package com.jones.mars.config;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Configuration
public class AuthInterceptor extends WebMvcConfigurerAdapter {

    @Autowired
    private UserService service;
    private static CountDownLatch tets = new CountDownLatch(1);
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 拦截配置
        addInterceptor.addPathPatterns("/**");

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/regist");
        addInterceptor.excludePathPatterns("/userLike/**");
        addInterceptor.excludePathPatterns("/user/auth/**");
        addInterceptor.excludePathPatterns("/comment/list");
        addInterceptor.excludePathPatterns("/comment/all");
        addInterceptor.excludePathPatterns("/user/verifyCode");
        addInterceptor.excludePathPatterns("/user/**/exists");
        addInterceptor.excludePathPatterns("/user/logout");
        addInterceptor.excludePathPatterns("/pano**");
        addInterceptor.excludePathPatterns("/file/**");
        addInterceptor.excludePathPatterns("/companyJoin/**");
        addInterceptor.excludePathPatterns("/home/**");
        addInterceptor.excludePathPatterns("/xunfei/**");
        addInterceptor.excludePathPatterns("/wechat/**");
        addInterceptor.excludePathPatterns("/translation/**");
        addInterceptor.excludePathPatterns("/enterpriseShown**");
        addInterceptor.excludePathPatterns("/swagger-ui.html");

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
            log.info("authorization in request header is :" + request.getHeader("authorization"));
            if (request.getCookies() == null || LoginUtil.getInstance().getUser() == null) {
                log.info("request address: " + request.getRemoteAddr());
                if("127.0.0.1".equals(getIp(request)) || (request.getHeader("referer") != null && request.getHeader("referer").contains("swagger-ui.html"))){
                    log.info("当前请求为内部接口请求，且无登录状态，设置默认用户为：13564332436");
                    UserLoginParam user = new UserLoginParam();
                    user.setMobile("13564332436");
                    user.setPassword("1234567801");
                    request.setAttribute("authorization", ((Map<String, String>)service.doLogin(user).getData()).get("authorization"));
                } else {
                    log.info("当前用户未登陆");
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "请登录后进行操作");
                    return false;
                }
            } else {
                log.info("当前访问： {}, 用户：{}", url, LoginUtil.getInstance().getUser().getMobile());
            }

            //跳转登录
//             has login, handle permission
//            if (hasPermission(handler)) {
//                return true;
//            } else {
//                response.sendError(HttpStatus.FORBIDDEN.value(), "没有权限");
//                log.warn("User {} are trying to access {}, but (s)he has no permission", LoginUtil.getInstance().getUser().getMobile(), url);
//                return false;
//            }
            return true;
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

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}