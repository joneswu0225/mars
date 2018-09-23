package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel(value="部门参数")
public class DepartmentUpdateParam {
    @NotBlank(message = "部门ID不能为空")
    @ApiModelProperty(value="部门ID",name="id")
    private Integer id;
    @NotBlank(message = "部门名称不能为空")
    @ApiModelProperty(value="部门名称",name="name")
    private String name;
    @ApiModelProperty(value="部门管理员ID",name="managerId", allowEmptyValue=true)
    private Integer managerId;
    @ApiModelProperty(value="上级部门ID",name="parentId", allowEmptyValue=true)
    private Integer parentId;
    @ApiModelProperty(value="部门人员ID列表",name="userIds", allowEmptyValue=true)
    private List<Integer> userIds;
}

