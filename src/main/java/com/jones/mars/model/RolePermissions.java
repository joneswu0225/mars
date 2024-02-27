package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.RolePermission;
import com.jones.mars.model.UserRole;
import com.jones.mars.model.param.RolePermissionParam;
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
@TableName("role_permission")
public class RolePermissions {
    private List<RolePermission> rolePermissionList = new ArrayList<>();

    public RolePermissions(RolePermissionParam param){
        for(String classId : param.getClassIds()){
            rolePermissionList.add(RolePermission.builder().roleId(param.getRoleId()).classId(classId).build());
        }
    }
}

