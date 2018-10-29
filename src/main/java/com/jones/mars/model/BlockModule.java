package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BlockModule {
    private Integer id;
    private String name;
    private Integer blockId;
    private List<BlockClass> classList = new ArrayList<>();
}

