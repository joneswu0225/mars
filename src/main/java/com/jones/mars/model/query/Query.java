package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ApiModel(value="分页参数")
public class Query {
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    @ApiParam(hidden = true)
    private Integer id;
    @Pattern(regexp = "\\d", message = "页码必须为数字")
    @ApiModelProperty(value="页码",name="page")
    private Integer page = DEFAULT_PAGE_NUM;
    @Pattern(regexp = "\\d", message = "每页长度必须为数字")
    @ApiModelProperty(value="每页长度",name="size")
    private Integer size = DEFAULT_PAGE_SIZE;
    @ApiParam(hidden = true)
    private Integer startRow;
    @ApiParam(hidden = true)
    private Object query;

    public Integer getStartRow(){
        Integer page = this.page < 1 ? 1 : this.page;
        return Integer.valueOf((page - 1) * size);
    }

    public Query(){}

    public Query(Object query){
        this.query = query;
    }
}
