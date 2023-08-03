package com.jones.mars.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.jones.mars.model.param.UserLikeParam;
import com.jones.mars.model.query.UserLikeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.UserLikeService;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.WechatApiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jsrecord")
@Slf4j
@Api(value = "jsrecord插件", tags = {"jsrecord插件"})
@Profile({"wechat"})
public class JsRecordController extends BaseController {

    @Autowired
    private WechatApiUtil util;

    @ApiOperation(value = "jsSDK签名", notes = "jsSDK签名")
    @PostMapping("sign")
    public BaseResponse sign(@RequestParam(name="url", required = true) @ApiParam(value="url",name="url") String url) {
        Map<String, Object> signResult = util.getJsSdkSign(url);
        Map<String, Object> result = new HashMap<>();
        result.put("c", 0);
        result.put("m", "");
        result.put("v", signResult);
        return BaseResponse.builder().data(result).build();
    }

    @ApiOperation(value = "素材下载", notes = "素材下载")
    @PostMapping("wxdown")
    public BaseResponse wxdown(@RequestParam(name="mediaId", required = true) @ApiParam(value="mediaId",name="mediaId") String mediaId) {
        log.info("start get wxdown mediaId: " + mediaId);
        String base64 = WechatApiUtil.getMediaBase64(mediaId);
        Map<String, String> value = new HashMap<>();
        value.put("mime", "audio/amr");
        value.put("data", base64);
        Map<String, Object> result = new HashMap<>();
        result.put("c", 0);
        result.put("m", "");
        result.put("v", value);
        return BaseResponse.builder().data(result).build();

    }

}
