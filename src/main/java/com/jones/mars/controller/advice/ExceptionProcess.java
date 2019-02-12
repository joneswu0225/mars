package com.jones.mars.controller.advice;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.object.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

/**
 * Created by jones on 18-12-24.
 */
@Slf4j
@ControllerAdvice
public class ExceptionProcess {
    // 对这个异常的统一处理，返回值 和Controller的返回规则一样
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public BaseResponse handleAll(Throwable t){
//        log.error("文件上传失败", t);
        return BaseResponse.builder().code(ErrorCode.UPLOAD_FAILED).data(t.getMessage()).build();
    }
}
