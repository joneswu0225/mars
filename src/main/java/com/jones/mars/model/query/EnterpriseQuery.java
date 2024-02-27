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
    private String userId;
    private String managerId;
    private Integer plateformFlg;


}

