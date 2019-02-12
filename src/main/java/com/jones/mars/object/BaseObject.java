package com.jones.mars.object;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Created by jones on 18-11-15.
 */
@Data
public class BaseObject {
    @ApiParam(hidden = true)
    protected Integer id;

}
