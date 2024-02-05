package com.jones.mars.controller;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.exception.MarsException;
import com.jones.mars.exception.NeedLoginException;
import com.jones.mars.exception.RequestException;
import com.jones.mars.object.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author yue.su
 * @date 2018年4月4日
 */
@ControllerAdvice(annotations = {RestController.class})
@Slf4j
public class ControllerAdvise {

	/**
	 * json格式请求 RequestBody valid报错
	 * 
	 * @param ex exception
	 * @return response
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@Order(400)
	public ResponseEntity<BaseResponse> handleValidationException(
			MethodArgumentNotValidException ex) {
		log.warn(ex.getMessage(), ex);

		return new ResponseEntity<>(new BaseResponse(ErrorCode.VALIDATION_FAILED,
				ex.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage())
						.collect(Collectors.joining(","))),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@Order(401)
	public ResponseEntity<BaseResponse> handleValidationException(ConstraintViolationException ex) {
		log.warn(ex.getMessage(), ex);

		return new ResponseEntity<>(
				new BaseResponse(ErrorCode.VALIDATION_FAILED, ex.getConstraintViolations().stream()
						.map(cons -> cons.getMessage()).collect(Collectors.joining(","))),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NeedLoginException.class)
	public ResponseEntity<BaseResponse> handleNeedLoginException(MarsException ex) {
		HttpStatus status;
		if (ex instanceof RequestException) {
			log.warn(ex.getMessage(), ex);
			status = HttpStatus.BAD_REQUEST;
		} else {
			log.error(ex.getMessage(), ex);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(new BaseResponse(ex.getCode(), ex.getMessage()), status);
	}

	@ExceptionHandler(MarsException.class)
	public ResponseEntity<BaseResponse> handleException(MarsException ex) {
		HttpStatus status;
		if (ex instanceof RequestException) {
			log.warn(ex.getMessage(), ex);
			status = HttpStatus.BAD_REQUEST;
		} else {
			log.error(ex.getMessage(), ex);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(new BaseResponse(ex.getCode(), ex.getMessage()), status);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<BaseResponse> handleException(RuntimeException ex) {
		log.error(ex.getMessage(), ex);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(new BaseResponse(ErrorCode.INTERNAL_ERROR, ex.getMessage()),
				status);
	}

}
