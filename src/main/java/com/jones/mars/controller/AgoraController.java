package com.jones.mars.controller;

import com.jones.mars.model.param.AgoraRtcParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.util.AgoraUtil;
import com.jones.mars.util.agora.RtcTokenBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/agora"})
@Api(value = "声网接口", tags = {"声网接口"})
@Profile({"media"})
public class AgoraController extends BaseController {

    /**
     * 声网rtc获取鉴权token
     */
    @ApiOperation(value = "rtc获取鉴权token", notes = "")
    @PostMapping("/rtc/token")
    public BaseResponse getRtcToken(@RequestBody @ApiParam(required=true) AgoraRtcParam param) {
        return BaseResponse.builder().data(AgoraUtil.getRtcToken(param.getUserId(), param.getChannelName(), param.getRole())).build();
    }

}
