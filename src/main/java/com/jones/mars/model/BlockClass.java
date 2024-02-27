package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("block_class")
public class BlockClass extends BaseObject {
    private String name;
    private String moduleId;
    private Integer operation;
    private Integer seq;
    private String permissionId;
}

