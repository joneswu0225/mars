package com.jones.mars.model;

import com.jones.mars.model.param.ProjectParam;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Project {
    public static final int EDITIND = 0;
    public static final int VERIFYING = 1;
    public static final int ONSHELF = 2;
    public static final int DOWNSHELF = 3;

    private Integer id;
    private String name;
    private String detail;
    private String imageUrl;
    private Integer blockId;
    private String blockName;
    private Integer moduleId;
    private Integer blockPlateformFlg;
    private Integer oriEnterpriseId;
    private String oriEnterpriseAvatar;
    private String oriEnterpriseName;
    private String oriEnterpriseBasePath;
    private String moduleName;
    private Integer classId;
    private Integer customFlg;
    private String className;
    private String status;
    private String reason;
    private Date publishDate;
    private Date createTime;
    private Date updateTime;
    private Integer deleteFlg;
    private List<ProjectUser> userList = new ArrayList<>();
//
//    public static ProjectBuilder projectBuilder(ProjectParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).imageUrl(param.getImageUrl())
//                .moduleId(param.getModuleId()).classId(param.getClassId()).oriEnterpriseId(param.getOriEnterpriseId());
//    }
}

