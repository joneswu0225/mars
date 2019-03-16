package com.jones.mars.controller;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.YoudaoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/translation"})
@Api(value = "语音相关", tags = {"语音相关"})
public class TranslationController extends BaseController {
    @Autowired
    private YoudaoUtil translator;

    @ApiOperation(value = "发音比对", notes = "发音比对")
    @PostMapping("/compare")
    @ResponseBody
    public BaseResponse list(@RequestParam(name="text", required = false) @ApiParam(value="文本内容",name="text") String text,
                             @RequestParam(name="content", required = false) @ApiParam(value="base64",name="content") String content) throws Exception {
//        BaseResponse resp = BaseResponse.builder().code(ErrorCode.INTERNAL_ERROR).build();
        String result = translator.getTranslateInfo(text, content);
        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
    }


}
