package com.jones.mars.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserParam {
    private Long blockId;
    private List<Long> userIds;
}

