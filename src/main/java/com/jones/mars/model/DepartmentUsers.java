package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.param.DepartmentUserParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("department_user")
public class DepartmentUsers {
    private List<DepartmentUser> departmentUserList = new ArrayList<>();

    public DepartmentUsers(DepartmentUserParam param){
        for(String userId : param.getUserIds()){
            departmentUserList.add(DepartmentUser.builder().departmentId(param.getDepartmentId()).userId(userId).build());
        }
    }
}

