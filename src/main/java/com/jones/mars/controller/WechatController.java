package com.jones.mars.controller;

import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.WechatApiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wechat"})
@Api(value = "微信接口", tags = {"微信接口"})
public class WechatController extends BaseController {
    @Autowired
    private WechatApiUtil wechatApiUtil;

    /**
     * 获取accessToken
     * @return
     */
    @ApiOperation(value = "获取accessToken", notes = "获取accessToken")
    @GetMapping(value="/accessToken")
    public BaseResponse getAccessToken(){
        return BaseResponse.builder().data(wechatApiUtil.getAccessToken()).build();
    }
    /**
     * 获取accessToken
     * @return
     */
    @ApiOperation(value = "获取accessToken", notes = "获取accessToken")
    @GetMapping(value="/jsTicket")
    public BaseResponse getJsTicket(){
        return BaseResponse.builder().data(wechatApiUtil.getJsTicket()).build();
    }
    /**
     * 获取accessToken
     * @return
     */
    @ApiOperation(value = "获取jsSDKSign", notes = "获取jsSDKSign")
    @GetMapping(value="/sign")
    public BaseResponse getJsSdkSign(@RequestParam(name="url", required = false) String url){
        return BaseResponse.builder().data(wechatApiUtil.getJsSdkSign(url)).build();
    }
    /**
     * 获取accessToken
     * @return
     */
    @ApiOperation(value = "获取jsSDKSign", notes = "获取jsSDKSign")
    @GetMapping(value="/media")
    public BaseResponse getMedia(@RequestParam(name="mediaId", required = true) String mediaId){
        return BaseResponse.builder().data(wechatApiUtil.getMediaBase64(mediaId)).build();
    }

}
