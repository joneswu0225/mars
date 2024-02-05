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
    private Long userId;
    private Long managerId;
    private Integer plateformFlg;


}

