package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlockHotspot extends BaseObject {
    private Integer seq;
    private Integer blockId;

    private Hotspot srcHotspot;
    private Hotspot destHotspot;
}

