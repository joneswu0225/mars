package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class Role{
  private Integer id;
  private String name;
  private Integer enterpriseId;
  private Integer blockId;
  private Date updateTime;
  private Date createTime;
}

