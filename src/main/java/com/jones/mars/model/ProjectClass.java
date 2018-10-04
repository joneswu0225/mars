package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectClass {
    private Integer id;
    private String name;
    private Integer moduleId;
    private Integer operation;
}

