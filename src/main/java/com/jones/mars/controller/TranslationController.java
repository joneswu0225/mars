package com.jones.mars.controller;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.model.param.TranslationParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.YoudaoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping({"/translation"})
@Api(value = "语音相关", tags = {"语音相关"})
public class TranslationController extends BaseController {
    @Autowired
    private YoudaoUtil translator;

    @ApiOperation(value = "发音比对", notes = "发音比对")
    @PostMapping("/compare")
    @ResponseBody
    public BaseResponse compare(@RequestBody @ApiParam(required=true) TranslationParam param) throws Exception {
//        BaseResponse resp = BaseResponse.builder().code(ErrorCode.INTERNAL_ERROR).build();
        String result = translator.getTranslateInfo(param.getText(), param.getContent());
        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
    }

    @ApiOperation(value = "发音比对2", notes = "发音比对,传语音")
    @PostMapping(value="/comparation")
    @ResponseBody
    public BaseResponse comparation(
            @NotNull(message = "语音不能为空") @RequestParam(name="audio") @ApiParam(value="语音",name="audio") MultipartFile file,
            @RequestParam(name="text", required = false) @ApiParam(value="文件名称",name="text") String text) throws Exception{
        String result = translator.getTranslateInfo(text, file);
        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
    }
}
