/**
 * 
 */
package com.jones.mars.exception;


import com.jones.mars.constant.ErrorCode;

/**
 * 表示系统的内部错误
 * 
 * <p>
 * 譬如数据库连接失败
 * </p>
 * 
 * @author yue.su
 * @date 2018年3月28日
 */
public class InternalException extends MarsException {


	/**
	 * @param message 错误消息
	 */
	public InternalException(String message) {
		super(ErrorCode.INTERNAL_ERROR, message);
	}

	/**
	 * @param message 错误消息
	 * @param cause 导致错误的异常
	 */
	public InternalException(String message, Throwable cause) {
		super(ErrorCode.INTERNAL_ERROR, message, cause);
	}

	/**
	 * @param cause 导致错误的异常
	 */
	public InternalException(Throwable cause) {
		super(ErrorCode.INTERNAL_ERROR, cause);
	}

}
