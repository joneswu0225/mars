package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(value="翻译参数")
public class TranslationParam {
    @ApiModelProperty(value="文本内容",name="text")
    private String text;
    @ApiModelProperty(value="语音字节流",name="audio")
    private MultipartFile audio;
    @ApiModelProperty(value="base64",name="content")
    private String content;
    @ApiModelProperty(value="发音人参数",name="voiceName")
    private String voiceName;
}

