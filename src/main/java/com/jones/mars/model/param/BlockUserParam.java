package com.jones.mars.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserParam {
    private Integer blockId;
    private List<Integer> userIds;
}

