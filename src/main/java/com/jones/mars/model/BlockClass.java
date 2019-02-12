package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockClass extends BaseObject {
    private String name;
    private Integer moduleId;
    private Integer operation;
}

