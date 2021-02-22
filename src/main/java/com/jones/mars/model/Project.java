package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Project extends BaseObject {
    public static final int CREATING = 0;
    public static final int EDITIND = 1;
    public static final int VERIFYING = 2;
    public static final int ONSHELF = 3;
    public static final int NOTPASS = 4;
    public static final int DOWNSHELF = 5;

    public static final int UNPUBLIC = 0;
    public static final int PUBLIC = 1;


    private String name;
    private String detail;
    private String imageUrl;
    private Integer blockId;
    private String blockName;
    private String blockPanoPath;
    private Integer moduleId;
    private Integer enterprisePlateformFlg;
    private Integer oriEnterpriseId;
    private String oriEnterpriseAvatar;
    private String oriEnterpriseName;
    private String oriEnterpriseBasePath;
    private String moduleName;
    private Integer classId;
    private Integer customFlg;
    private Integer publicFlg = UNPUBLIC;
    private String className;
    private Integer status = EDITIND;
    private String reason;
    private Date publishDate;
    private Integer creatorId;
    private String creatorSgname;
    private String creatorAvator;
    private Date updateTime;
    private Date createTime;
    private Integer deleteFlg;
    private List<ProjectUser> userList = new ArrayList<>();
    private List<Hotspot> attachments = new ArrayList<>();
    private List<Hotspot> guidances = new ArrayList<>();
//
//    public static ProjectBuilder projectBuilder(ProjectParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).imageUrl(param.getImageUrl())
//                .moduleId(param.getModuleId()).classId(param.getClassId()).oriEnterpriseId(param.getOriEnterpriseId());
//    }
}

