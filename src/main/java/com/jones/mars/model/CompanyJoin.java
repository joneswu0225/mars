package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CompanyJoin extends BaseObject {
    private String companyName;
    private String contactor;
    private String title;
    private String email;
    private String mobile;
    private String remark;
    private Integer status;
    private Date updateTime;
    private Date createTime;

//    public static CompanyJoinBuilder companyJoinBuilder(CompanyJoinParam param){
//        return builder().companyName(param.getCompanyName()).contactor(param.getContactor()).mobile(param.getMobile()).title(param.getTitle())
//                .email(param.getEmail());
//    }
}
