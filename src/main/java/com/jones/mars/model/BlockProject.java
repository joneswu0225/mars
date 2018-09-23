package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockProject {
  private Integer id;
  private Integer projectId;
  private Integer blockId;
}

