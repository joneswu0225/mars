package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.List;

@Data
public class BlockUserParam {
    private Integer blockId;
    private List<Integer> userIds;
}

