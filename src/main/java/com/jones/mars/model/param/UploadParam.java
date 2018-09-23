package com.jones.mars.model.param;

import com.jones.mars.model.constant.FileType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="文件上传参数")
public class UploadParam {
    @NotNull(message = "上传文件不能为空")
    @ApiModelProperty(value="上传文件",name="file")
    private MultipartFile file;
    @ApiModelProperty(value="文件名称",name="fileName")
    private String fileName;
    @ApiModelProperty(value="关联内容的id， 如上传名片为userId, 上传企业logo为enterpirseId",name="relatedId")
    private Integer relatedId;
    @ApiModelProperty(value="文件类型",name="fileType")
    private FileType fileType;
}

