package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelResourceAuthParam extends BaseObject {
    @ApiModelProperty(value="校验信息",name="validateContent")
    private String validateContent;
    @ApiModelProperty(value="临时校验token",name="token")
    private String token;
}

