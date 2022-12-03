/**
 * 
 */
package com.jones.mars.exception;


import com.jones.mars.constant.ErrorCode;

/**
 * 用户未登录错误
 */
public class NeedLoginException extends MarsException {


	/**
	 * @param message 错误消息
	 */
	public NeedLoginException(String message) {
		super(ErrorCode.NEED_LOGIN, message);
	}

	/**
	 * @param message 错误消息
	 * @param cause 导致错误的异常
	 */
	public NeedLoginException(String message, Throwable cause) {
		super(ErrorCode.NEED_LOGIN, message, cause);
	}

	/**
	 * @param cause 导致错误的异常
	 */
	public NeedLoginException(Throwable cause) {
		super(ErrorCode.NEED_LOGIN, cause);
	}

}
