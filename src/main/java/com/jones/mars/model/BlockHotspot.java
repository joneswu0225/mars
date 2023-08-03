package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockHotspot extends BaseObject {
    private Integer seq;
    private Integer blockId;

    private Hotspot srcHotspot;
    private Hotspot destHotspot;
}

