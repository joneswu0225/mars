/**
 * 通联数据机密 -------------------------------------------------------------------- 通联数据股份公司版权所有 ©
 * 2013-1016
 * 
 * 注意：本文所载所有信息均属于通联数据股份公司资产。本文所包含的知识和技术概念均属于 通联数据产权，并可能由中国、美国和其他国家专利或申请中的专利所覆盖，并受商业秘密或 版权法保护。
 * 除非事先获得通联数据股份公司书面许可，严禁传播文中信息或复制本材料。
 * 
 * DataYes CONFIDENTIAL --------------------------------------------------------------------
 * Copyright © 2013-2016 DataYes, All Rights Reserved.
 * 
 * NOTICE: All information contained herein is the property of DataYes Incorporated. The
 * intellectual and technical concepts contained herein are proprietary to DataYes Incorporated, and
 * may be covered by China, U.S. and Other Countries Patents, patents in process, and are protected
 * by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained from DataYes.
 */
package com.jones.mars.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;

/**
 * @author qiong.wu
 * @date 2018年12月23日
 */
@Configuration
public class MultipartsConfig {
	/**
	 * 文件上传配置
	 *
	 * @return MultipartConfigElement
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement(
			@Value("${multipart.maxFileSize}") String maxFileSize,
			@Value("${multipart.maxRequestSize}") String maxRequestSize) {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个文件最大
		factory.setMaxFileSize(maxFileSize);
		// 设置总上传数据总大小
		factory.setMaxRequestSize(maxRequestSize);
		return factory.createMultipartConfig();
	}

}
