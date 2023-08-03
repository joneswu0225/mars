package com.jones.mars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeployLicense {
    private String name;
    private String detail;
    private String keyPublic;
    private String keyPrivate;
    private String diskSeries;
    private String direcotry;
    private String contentOri;
    private String contentEnc;
    private Date startTime;
    private Date expireTime;
    private Date createTime;
}
