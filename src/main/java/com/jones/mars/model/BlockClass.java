package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockClass {
    private Integer id;
    private String name;
    private Integer moduleId;
    private Integer operation;
}

