package com.jones.mars.exception;


import com.jones.mars.constant.ErrorCode;

/**
 * @author yue.su
 * @date 2018年3月28日
 */
public class MarsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 457106199811190700L;
	private ErrorCode code;

	public MarsException() {
		super(ErrorCode.INTERNAL_ERROR.description);
		this.code = ErrorCode.INTERNAL_ERROR;
	}

	public MarsException(String msg) {
		super(msg);
		this.code = ErrorCode.INTERNAL_ERROR;
	}

	/**
	 * @param code 错误代码
	 */
	public MarsException(ErrorCode code) {
		super(code.description);
		this.code = code;
	}

	/**
	 * @param code 错误代码
	 * @param message 错误消息
	 */
	public MarsException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * @param code 错误代码
	 * @param cause 导致错误的异常
	 */
	public MarsException(ErrorCode code, Throwable cause) {
		super(cause);
		this.code = code;
	}


	/**
	 * @param code 错误代码
	 * @param message 错误消息
	 * @param cause 导致错误的异常
	 */
	public MarsException(ErrorCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}

}
