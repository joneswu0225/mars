package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVisit extends BaseObject{
    private String username = "Anonymous";
    private String path;
    private String srcIp;
    private Integer roleId;
    private Date createTime;

//    public UserVisit(String path, String srcIp) {
//        this.path = path;
//        this.srcIp = srcIp;
//    }

}

