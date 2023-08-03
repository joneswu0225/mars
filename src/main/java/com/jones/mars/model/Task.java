package com.jones.mars.model;

import com.jones.mars.model.constant.TaskType;
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
public class Task extends BaseObject {
    public static final int CREATING = 0;
    public static final int WORKING = 1;
    public static final int FINISHED = 2;
    public static final int EXPIRED = 3;

    public static final int CURRENT_TASK = 1;
    public static final int OLD_TASK = 0;

    private Integer id;
    private String name;
    private String detail;
    private String type = TaskType.PROJECT_MODIFY.name();
    private Integer blockId;
    private Integer userId;
    private Integer status = CREATING;
    private Date startDate;
    private Date expireDate;
    private Integer currentFlg;
    private Integer deleteFlg;
    private Integer version;
    private Integer createBy;
    private Date createTime;
    private Date updateTime;
    private String creatorSgname;
    private String updaterSgname;
    private Project project;

//
//    public static ProjectBuilder projectBuilder(ProjectParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).imageUrl(param.getImageUrl())
//                .moduleId(param.getModuleId()).classId(param.getClassId()).oriEnterpriseId(param.getOriEnterpriseId());
//    }
}

