package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BlockSceneType extends BaseObject {
  private Integer blockId;
  private String name;
  private String detail;
  private List<Scene> sceneList = new ArrayList<>();
}

