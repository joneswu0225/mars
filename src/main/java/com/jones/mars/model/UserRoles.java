package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.UserRole;
import com.jones.mars.model.param.UserRoleParam;
import com.jones.mars.object.BaseObject;
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
@TableName("user_role")
public class UserRoles extends BaseObject {
    private List<UserRole> userRoleList = new ArrayList<>();

    public UserRoles(UserRoleParam param){
        for(String userId : param.getUserIds()){
            userRoleList.add(UserRole.builder().roleId(param.getRoleId()).userId(userId).build());
        }
    }
}

