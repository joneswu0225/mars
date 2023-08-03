package com.jones.mars.object;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jones on 18-11-15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseObject {
    @ApiParam(hidden = true)
    protected Integer id;

}
