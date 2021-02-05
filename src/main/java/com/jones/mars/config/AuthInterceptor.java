package com.jones.mars.config;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.User;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.UserLoginParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.ProjectService;
import com.jones.mars.service.UserService;
import com.jones.mars.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.rmi.runtime.Log;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Configuration
public class AuthInterceptor extends WebMvcConfigurerAdapter {

    @Autowired
    private UserService service;
    @Autowired
    private ProjectService projectService;
    private static CountDownLatch tets = new CountDownLatch(1);
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }
    @Bean
    public ProjectModifyInterceptor getProjectModifyInterceptor() {
        return new ProjectModifyInterceptor();
    }
    @Bean
    public TotalInterceptor getTotalInterceptor() {
        return new TotalInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        InterceptorRegistration projectAuthInterceptor = registry.addInterceptor(getProjectModifyInterceptor());
        InterceptorRegistration totalInterceptor = registry.addInterceptor(getTotalInterceptor());
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
        projectAuthInterceptor.addPathPatterns("/project/**");
        totalInterceptor.addPathPatterns("/**");

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/wxlogin");
        addInterceptor.excludePathPatterns("/user/wxUpdatePassword");
        addInterceptor.excludePathPatterns("/user/password/reset");
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
        addInterceptor.excludePathPatterns("/jsrecord/**");
        addInterceptor.excludePathPatterns("/companyJoin/**");
        addInterceptor.excludePathPatterns("/home/**");
        addInterceptor.excludePathPatterns("/xunfei/**");
        addInterceptor.excludePathPatterns("/wechat/**");
        addInterceptor.excludePathPatterns("/translation/**");
        addInterceptor.excludePathPatterns("/enterpriseShown**");
        addInterceptor.excludePathPatterns("/swagger-ui.html");

    }

    private class TotalInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String appSource = request.getHeader(CommonConstant.APP_SOURCE_FIELD);
            log.info("appSource: " + appSource);
            if("/user/login".equals(request.getRequestURI())){
                request.setAttribute("appSource", appSource);
            }
            return true;
        }
    }
    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String appSource = request.getHeader(CommonConstant.APP_SOURCE_FIELD);
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
            if(request.getHeader("authorization") != null && LoginUtil.getInstance().getUser() == null){
                String authorization = request.getHeader("authorization");
                authorization = authorization.split(" ")[0];
                User loginUser = LoginUtil.getInstance().refreshLoginUser(authorization);
                if(loginUser == null){
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "请登录后进行操作");
                    return false;
                }
            }
            if (request.getCookies() == null || LoginUtil.getInstance().getUser() == null) {
                log.info("request address: " + request.getRemoteAddr());
                if("127.0.0.1".equals(getIp(request)) || (request.getHeader("referer") != null && request.getHeader("referer").contains("swagger-ui.html"))){
                    log.info("当前请求为内部接口请求，且无登录状态，设置默认用户为：admin");
                    UserLoginParam user = UserLoginParam.builder().mobile("admin").password("admin").build();
                    request.setAttribute("authorization", ((Map<String, String>)service.doLogin(user, appSource).getData()).get("authorization"));
                } else {
                    log.info("当前用户未登陆");
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "请登录后进行操作");
                    return false;
                }
            } else {
                log.info("当前访问： {}, 用户：{}", url, LoginUtil.getInstance().getUser().getMobile());
            }

            return true;
        }
    }

    private class ProjectModifyInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            log.info(String.format("用户[ %s ] %s 访问　%s", LoginUtil.getInstance().getUser().getMobile(), request.getMethod(), request.getRequestURI()));
            List<RequestMethod> httpMethods = Arrays.asList(new RequestMethod[]{RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.PATCH});
            if(httpMethods.contains(RequestMethod.valueOf(request.getMethod()))){
                String requestUri = request.getRequestURI().replace("//","/");
                String[] pathParams = requestUri.split("/");
                if(pathParams.length > 2 && pathParams[2].matches("\\d+")){
                    String projectId = pathParams[2];
                    ErrorCode errorCode = projectService.projectModifyAuthError(Integer.parseInt(projectId));
                    if(errorCode != null){
                        response.setContentType("application/json; charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.print(JSONObject.toJSONString(BaseResponse.builder().code(errorCode).build()));
                        writer.close();
                        response.flushBuffer();
                        return false;
                    }
                }
            }
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