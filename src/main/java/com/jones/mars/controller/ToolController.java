package com.jones.mars.controller;

import com.jones.mars.constant.ApplicationConst;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.Snowflake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/tool"})
@Api(value = "工具接口", tags = {"工具接口"})
public class ToolController extends BaseController {
    /**
     * 生成id
     * @return
     */
    @ApiOperation(value = "生成id", notes = "生成id")
    @GetMapping(value="/generateIds")
    public BaseResponse generateIds(@RequestParam(name="tableName", required = true) String tableName,
                                       @RequestParam(name="deployId", required = false) Integer deployId,
                                       @RequestParam(name="size", required = false, defaultValue = "1") int size){
        Integer tableId = ApplicationConst.getTableId(tableName);
        if (tableId <= 0){
            return BaseResponse.builder().code(ErrorCode.INTERNAL_ERROR).message("表名不存在!").build();
        }
        size = size > 0 ? size : 1;
        deployId = deployId == null ? ApplicationConst.DEPLOY_ID : deployId;
        Snowflake snowflake = new Snowflake(deployId, tableId);
        List<String> ids = new ArrayList<>(size);
        for(int i=0; i<size;i++){
            ids.add(snowflake.nextIdStr());
        }
        return BaseResponse.builder().data(ids).build();
    }

}
