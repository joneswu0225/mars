package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockProject extends BaseObject {
  private Integer projectId;
  private Integer blockId;
}

