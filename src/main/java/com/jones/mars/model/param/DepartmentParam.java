package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ApiModel(value="部门参数")
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentParam extends BaseObject {
    @NotBlank(message = "部门名称不能为空")
    @ApiModelProperty(value="部门名称",name="name")
    private String name;
    @NotNull(message = "企业ID不能为空")
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @ApiModelProperty(value="上级部门ID",name="parentId")
    private Integer parentId;
    @ApiModelProperty(value="部门负责人ID",name="managerId")
    private Integer managerId;
    @ApiParam(hidden = true)
    private Integer departmentId;
    @ApiParam(hidden = true)
    private Integer operatorId;
    @ApiModelProperty(value="部门人员ID列表",name="userIds", allowEmptyValue=true)
    private List<Integer> userIds;
}

