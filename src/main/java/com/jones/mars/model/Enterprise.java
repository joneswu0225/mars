package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.EnterpriseParam;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("enterprise")
public class Enterprise extends BaseObject {
    private String name;
    private String detail;
    private String avatar;
    private String imagePath;
    private String basePath;
    private Date updateTime;
    private Date createTime;
    private Integer plateformFlg = CommonConstant.NOPLATEFROM;
    private Integer managerFlg = EnterpriseUser.ENTERPRISE_NORMAL;
    private List<Block> blockList = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();

//    public static EnterpriseBuilder enterpriseBuilder(EnterpriseParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).managerId(param.getManagerId());
//    }

}

