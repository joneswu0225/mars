package com.jones.mars.controller;

import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.WechatApiUtil;
import com.jones.mars.util.WechatWeProgramUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/wechat"})
@Profile({"wechat"})
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

    /**
     * 小程序Code2SessionKey
     * @return
     */
    @ApiOperation(value = "小程序Code2SessionKey", notes = "小程序Code2SessionKey")
    @GetMapping(value="/weprogram/{weprogramId}/wxCode2SessionKey")
    public BaseResponse wxCode2SessionKey(@PathVariable String weprogramId, @RequestParam(name="code", required = true) String code){
        return BaseResponse.builder().data(WechatWeProgramUtil.getSessionKey(weprogramId, code)).build();
    }
    /**
     * 小程序wxDecryptedUserInfo
     * @return
     */
    @ApiOperation(value = "小程序wxDecryptedUserInfo", notes = "小程序wxDecryptedUserInfo")
    @GetMapping(value="/wxDecryptedUserInfo")
    public BaseResponse wxDecryptedUserInfo(@RequestParam(name="sessionKey", required = true) String sessionKey,
                                            @RequestParam(name="encryptedData", required = true) String encryptedData,
                                            @RequestParam(name="iv", required = true) String iv){
        return BaseResponse.builder().data(WechatWeProgramUtil.getDecryptedUserInfo(sessionKey, encryptedData,iv)).build();
    }
    /**
     * 小程序appid
     * @return
     */
    @ApiOperation(value = "小程序appid", notes = "小程序appid")
    @GetMapping(value="/wxAppid")
    public BaseResponse wxAppid(){
        return BaseResponse.builder().data(WechatApiUtil.appId).build();
    }

}
