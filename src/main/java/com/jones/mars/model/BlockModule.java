package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BlockModule extends BaseObject {
    private String name;
    private Integer blockId;
    private List<BlockClass> classList = new ArrayList<>();
}

