package com.jones.mars.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class UserVisit {
    private Integer id;
    private String username = "Anonymous";
    private String path;
    private String srcIp;
    private Integer roleId;
    private Date insertTime;

    public UserVisit(String path, String srcIp) {
        this.path = path;
        this.srcIp = srcIp;
    }

}

