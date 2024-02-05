package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("deploy_license")
public class DeployLicense {
    private Long id;
    private String name;
    private String detail;
    private String path;
    private String key;
    private String diskSeries;
    private String contentOri;
    private String contentEnc;
    private Date startDate;
    private Date expireDate;
    private Date createTime;
}
