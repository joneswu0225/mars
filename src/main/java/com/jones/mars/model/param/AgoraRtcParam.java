package com.jones.mars.model.param;

import com.jones.mars.util.agora.RtcTokenBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="声网rtc鉴权参数")
@NoArgsConstructor
@AllArgsConstructor
public class AgoraRtcParam {
    @ApiModelProperty(value="用戶ID",name="userId")
    private Long userId;
    @ApiModelProperty(value="频道名称",name="channelName")
    private String channelName;
    @ApiModelProperty(value="频道发送接收角色，Role_Publisher：发送；Role_Subscriber：接收",name="role")
    private RtcTokenBuilder.Role role;
}

