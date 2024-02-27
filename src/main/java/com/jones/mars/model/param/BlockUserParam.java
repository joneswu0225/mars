package com.jones.mars.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserParam {
    private String blockId;
    private List<String> userIds;
}

