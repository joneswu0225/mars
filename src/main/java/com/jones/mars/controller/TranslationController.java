package com.jones.mars.controller;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.param.TranslationParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.YoudaoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/translation"})
@Api(value = "语音相关", tags = {"语音相关"})
@Profile({"media"})
public class TranslationController extends BaseController {
    @Autowired
    private YoudaoUtil translator;

    @ApiOperation(value = "发音比对", notes = "发音比对")
    @PostMapping("/compare")
    @ResponseBody
    public BaseResponse compare(@RequestBody @ApiParam(required=true) TranslationParam param) throws Exception {
        String result = translator.getTranslateInfo(param);
        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
    }

    @ApiOperation(value = "发音比对2", notes = "发音比对,传语音")
    @PostMapping(value="/comparation")
    @ResponseBody
    public BaseResponse comparation(
            @RequestParam(name="audio") @ApiParam(value="语音",name="audio") MultipartFile file,
            @RequestParam(name="text", required = false) @ApiParam(value="文本內容",name="text") String text) throws Exception{
        String result = translator.getTranslateInfo(text, file);
        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
    }
}
