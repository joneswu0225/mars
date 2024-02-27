package com.jones.mars.model.param;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("enterprise_user")
public class EnterpriseUserParam extends BaseObject {
    @ApiParam(hidden = true)
    private String enterpriseId;
    @ApiModelProperty(value="用户ID",name="userId")
    private String userId;
    @ApiModelProperty(value="企业管理员",name="managerFlg")
    private Integer managerFlg;
}

