package com.jones.mars.model;

import lombok.Data;

@Data
public class GeneralResponse {
    private boolean suc;
    private String msg;

    public GeneralResponse(boolean suc, String msg) {
        setSuc(suc);
        setMsg(msg);
    }

}

