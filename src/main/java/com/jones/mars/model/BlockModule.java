package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockModule extends BaseObject {
    private String name;
    private Integer blockId;
    private Integer seq;
    private String blockName;
    private List<BlockClass> classList = new ArrayList<>();
}

