package com.jones.mars.constant;


import com.jones.mars.exception.MarsException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yue.su
 * @date 2018年3月28日
 */
public enum ErrorCode {

	OK("0", "成功"),

	//注册错误
	REGIST_MOBILE_EXISTS("1001", "手机号已被注册"),

	//登录错误
	LOGIN_MOBILE_NOTEXISTS("1002", "手机号不存在"),
	LOGIN_VERIFYCODE_WRONG("1003", "验证码错误"),

	//请求错误
	VALIDATION_FAILED("100001", "参数校验失败"),
	BAD_REQUEST("100002", "请求参数错误 [%s]"),

	//数据错误
	UNSUPPORTED_DATABASE("300001", "不支持的数据库"), 
	UNSUPPORTED_COLUMN("300002","不支持的字段类型"), 
	CONVERT_NOT_SUPPORT("300003","类型转换错误"), 
	CONVERT_OVERFLOW("300004", "类型转换溢出"),

	//客户端错误
	CANNOT_CONNECT_TO_SERVER("500001", "无法连接服务器"),
	
	INTERNAL_ERROR("9000", "系统内部错误"),

//	FORM_ERROR("8999", "表单错误")

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
