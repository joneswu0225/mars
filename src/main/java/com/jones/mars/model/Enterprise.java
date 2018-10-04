package com.jones.mars.model;

import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.EnterpriseParam;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Enterprise {
    private Integer id;
    private String name;
    private String detail;
    private String avatar;
    private String imagePath;
    private Integer managerId;
    private String managerName;
    private String basePath;
    private Date updateTime;
    private Date createTime;
    private Integer plateformFlg = CommonConstant.NOPLATEFROM;
    private List<Block> blockList = new ArrayList<>();

    public static EnterpriseBuilder enterpriseBuilder(EnterpriseParam param){
        return builder().name(param.getName()).detail(param.getDetail()).managerId(param.getManagerId());
    }

}

