package com.jones.mars.object;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value="id", type= IdType.ASSIGN_ID)
    protected String id;

}
