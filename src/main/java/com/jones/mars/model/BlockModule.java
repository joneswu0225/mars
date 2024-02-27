package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("block_module")
public class BlockModule extends BaseObject {
    private String name;
    private String blockId;
    private Integer seq;
    private String blockName;
    private List<BlockClass> classList = new ArrayList<>();
}

