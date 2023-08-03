package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockClass extends BaseObject {
    private String name;
    private Integer moduleId;
    private Integer operation;
    private Integer id;
    private Integer seq;
    private Integer permissionId;
}

