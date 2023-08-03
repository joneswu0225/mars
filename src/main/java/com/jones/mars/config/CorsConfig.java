package com.jones.mars.config;

import com.jones.mars.model.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//拿过来粘贴一波，跨域问题就解决了
		/**
		 * 意思就是，无论你请求我8181（本项目端口）的什么服务，
		 * 而且是GET、HEAD、POST等几种请求的类型都可以
		 */
		// 设置允许跨域的路由
		registry.addMapping("/**")
				// 设置允许跨域请求的域名
				.allowedOriginPatterns("*")
				// 是否允许证书（cookies）
				.allowCredentials(true)
				.allowedHeaders("*")
				// 设置允许的方法
				.allowedMethods("*");
				// 跨域允许时间
//				.maxAge(3600);
	}

}
