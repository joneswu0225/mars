package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockSceneType extends BaseObject {
  private Integer blockId;
  private String name;
  private String detail;
}

