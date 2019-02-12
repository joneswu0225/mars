package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseShown extends BaseObject {
    private String name;
    private String imageUrl;
    private Integer enterpriseId;
    private Date updateTime;
    private Date createTime;

}

