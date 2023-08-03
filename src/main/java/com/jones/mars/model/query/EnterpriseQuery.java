package com.jones.mars.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseQuery extends Query {

    private String name;
    private Integer userId;
    private Integer managerId;
    private Integer plateformFlg;


}

