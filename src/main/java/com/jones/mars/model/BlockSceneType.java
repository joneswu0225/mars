package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("block_scene_type")
public class BlockSceneType extends BaseObject {
  private String blockId;
  private String relSceneTypeId;
  private String name;
  private String detail;
  private Integer seq;
  private List<Scene> sceneList = new ArrayList<>();
}

