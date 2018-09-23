package com.jones.mars.model;

import lombok.Data;

@Data
public class GeneralResponse {
    private int errorCode = 0;
    private String errorMsg;
    private Object data;

    public GeneralResponse(){}

    public GeneralResponse(Object data){
        setData(data);
    }
    public GeneralResponse(int errorCode, String errorMsg) {
        setErrorCode(errorCode);
        setErrorMsg(errorMsg);
    }

}

