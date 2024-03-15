package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockTipParams extends BaseObject {
    List<BlockTipParam> blockTipParams = new ArrayList<>();
}

