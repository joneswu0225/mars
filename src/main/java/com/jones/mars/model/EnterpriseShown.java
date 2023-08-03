package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseShown extends BaseObject {
    private String name;
    private String imageUrl;
    private Integer enterpriseId;
    private Date updateTime;
    private Date createTime;

}

