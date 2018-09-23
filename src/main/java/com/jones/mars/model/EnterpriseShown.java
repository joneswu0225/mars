package com.jones.mars.model;

import com.jones.mars.model.param.EnterpriseShownParam;
import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseShown {
    private Integer id;
    private String name;
    private String imageUrl;
    private Integer enterpriseId;
    private Date updateTime;
    private Date createTime;

    public EnterpriseShown(){}

    public EnterpriseShown(EnterpriseShownParam param){
        this.name = param.getName();
        this.imageUrl = param.getImageUrl();
        this.enterpriseId = param.getEnterpriseId();
    }
}

