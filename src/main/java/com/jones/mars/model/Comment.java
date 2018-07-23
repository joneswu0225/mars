 package com.jones.mars.model;

 import lombok.Data;

 import java.util.Date;

 @Data
 public class Comment
 {
   private Integer commentId;
  private User user = new User();
  private Hotspot hotspot = new Hotspot();
   private Date inserttime;
 }

