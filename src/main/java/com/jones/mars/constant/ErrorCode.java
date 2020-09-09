package com.jones.mars.constant;


import com.jones.mars.exception.MarsException;

import java.util.HashMap;
import java.util.Map;


public enum ErrorCode {

	OK("0", "成功"),

	//注册错误
	REGIST_MOBILE_EXISTS("1001", "手机号已被注册"),

	//登录错误
	LOGIN_MOBILE_NOTEXISTS("1002", "手机号不存在"),
	LOGIN_FAIL("1003", "验证码或密码错误"),
	WECHAT_LOGIN_VERIFY_FAIL("1004", "微信小程序登录验证失败"),
	WECHAT_NO_PASSWD("1005", "微信小程序注册没有密码"),

	//请求错误
	VALIDATION_FAILED("1011", "参数校验失败"),
	BAD_REQUEST("1012", "请求参数错误 [%s]"),
	UPLOAD_FAILED("1013", "文件上传失败"),

	// 项目审核
	PROJECT_STATUS_ERROR("4000", "项目状态错误"),
	PROJECT_VERIFY_VERIFIED("4001", "项目状态错误，编辑状态下才能提交审核"),
	PROJECT_VERIFY_ONSHELFED("4002", "项目状态错误，提交审核后才能上架"),
	PROJECT_VERIFY_OFFSHELFED("4003", "项目状态错误，上架的项目才能下架"),
	PROJECT_VERIFY_REMODIFY("4004", "项目状态错误，下架的项目才能退回编辑"),

	// 项目相关
	PROJECT_DELETE_DENIED("5001", "项目已经编辑过，无法删除"),

	//权限不够
	AUTH_PROJECT_UNPUBLIC_NOLOGIN("2001", "非登录状态无权访问非公开项目"),
	AUTH_PROJECT_UNAUTH("2002", "无权访问该项目"),
	//业务错误
	ENT_USER_EXISTS("3001", "企业用户已存在"),
	//角色错误
	ROLE_DELETE_EXIST_USER("5001", "该角色仍存在被授予的用户，无法删除"),
	ROLE_PERMISSION_DELETE_NOAUTH("5101", "请联系管理员进行权限删除"),

	INTERNAL_ERROR("9000", "系统内部错误"),
	;

	public final String key;

	public final String description;

	private ErrorCode(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public boolean isSucceeded() {
		return this == OK;
	}

	public static boolean isSucceeded(String thatKey) {
		return OK.key.equals(thatKey);
	}

	// public static boolean isBadRequest(String keyName) {
	// // 原则：如果是未知原因，就认为是我们系统自己的问题
	//
	// if (keyName == null)
	// return false;
	// ErrorCode code = ErrorCodeMap.byKey(keyName);
	// if (code == null)
	// return false;
	// return code.INTERNAL_ERROR;
	// }

	private static class ErrorCodeMap {

		private static final Map<String, ErrorCode> mapByKey = new HashMap<>();

		static {
			for (ErrorCode er : ErrorCode.values()) {
				String key = er.key;
				if (mapByKey.containsKey(key)) {
					throw new MarsException(ErrorCode.INTERNAL_ERROR,
							"duplicated error code key: " + key);
				}

				mapByKey.put(key, er);
			}
		}

		private ErrorCodeMap() {
			// dummy
		}

		public static ErrorCode byKey(String key) {
			return mapByKey.get(key);
		}

	}

}
