package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProjectModule {
    private Integer id;
    private String name;
    private Integer blockId;
    private List<ProjectClass> classList = new ArrayList<>();
}

