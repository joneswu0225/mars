package com.jones.mars.model;

import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.BlockParam;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Block {
    private Integer id;
    private Integer plateformFlg = CommonConstant.NOPLATEFROM;
    private String detail;
    private Integer operatorId;
    private Date updateTime;
    private Date createTime;
    private String status;
    private Integer enterpriseId;
    private String name;
    private List<BlockModule> moduleList = new ArrayList<>();

    public static BlockBuilder blockBuilder(BlockParam param){
        return builder().name(param.getName()).enterpriseId(param.getEnterpriseId()).detail(param.getDetail()).plateformFlg(param.getPlateformFlg());
    }

    public void setModules(List<Role> roles){

    }
}

