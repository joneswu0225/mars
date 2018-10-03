package com.jones.mars.model.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnterpriseQuery extends Query {

    private String name;
    private Integer userId;
    private Integer plateformFlg;


}

