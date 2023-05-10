package com.jones.mars.controller;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.TranslationParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.XunfeiUtil;
import com.jones.mars.util.YoudaoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping({"/youdao"})
@Api(value = "语音相关", tags = {"语音相关"})
public class YoudaoController extends BaseController {
    /**
     * 有道语音评测
     * TranslationParam.content 语音的base64
     * TranslationParam.text 语音对应的实际文本
     * @return
     * @throws UnsupportedEncodingException
     */
//    @ApiOperation(value = "有道语音评测base64", notes = "有道语音评测base64")
//    @PostMapping("/ise/param")
//    @ResponseBody
//    public BaseResponse getISEInfo2(@RequestBody @ApiParam(required=true) TranslationParam param) throws UnsupportedEncodingException {
//        String result = XunfeiUtil.getISEInfo(param.getContent(), param.getText());
//        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
//    }

//    @ApiOperation(value = "有道语音评测", notes = "有道语音评测")
//    @PostMapping(value="/ise")
//    @ResponseBody
//    public BaseResponse getISEInfo(
//            @NotNull(message = "语音不能为空") @RequestParam(name="audio") @ApiParam(value="语音",name="audio") MultipartFile file,
//            @RequestParam(name="text", required = false) @ApiParam(value="内容",name="text") String text) throws Exception{
//        String result = XunfeiUtil.getISEInfo(file, text);
//        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
//    }

    /**
     * 语音听写
     * TranslationParam.content 语音的base64
     * @return
     * @throws UnsupportedEncodingException
     */
//    @ApiOperation(value = "有道语音听写base64", notes = "有道语音听写base64")
//    @PostMapping("/iat/param")
//    @ResponseBody
//    public BaseResponse getIATInfo2(@RequestBody @ApiParam(required=true) TranslationParam param) throws UnsupportedEncodingException {
//        String result = XunfeiUtil.getIATInfo(param.getContent());
//        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
//    }
//    @ApiOperation(value = "讯飞语音听写", notes = "讯飞语音听写")
//    @PostMapping("/iat")
//    @ResponseBody
//    public BaseResponse getIATInfo(@NotNull(message = "语音不能为空") @RequestParam(name="audio") @ApiParam(value="语音",name="audio") MultipartFile file) throws Exception {
//        String result = XunfeiUtil.getIATInfo(file);
//        return BaseResponse.builder().data(JSONObject.parseObject(result)).build();
//    }

    /**
     * 语音合成
     * TranslationParam.text 语音对应的实际文本
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "有道语音合成", notes = "有道语音合成")
    @PostMapping("/tts")
    @ResponseBody
    public BaseResponse getTTSInfo(@RequestParam(name="text", required = true) @ApiParam(value="内容",name="text") String text,
                                   @RequestParam(name="langType", required = false) @ApiParam(value="中英文,zh-CHS/en",name="langType") String langType,
                                   @RequestParam(name="voice", required = false) @ApiParam(value="",name="voice") String voice) throws Exception {
        String result = YoudaoUtil.getTTSInfo(text, langType, voice);
        result = CommonConstant.UPLOAD_PATH_TMP_AUDIO + File.separator + result;
        return BaseResponse.builder().data(result).build();
    }

}
