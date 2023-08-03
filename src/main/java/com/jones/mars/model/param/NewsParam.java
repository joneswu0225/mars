package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value="新闻动态")
@NoArgsConstructor
@AllArgsConstructor
public class NewsParam extends BaseObject {
    @ApiModelProperty(value="内容url",name="contentUrl")
    private String contentUrl;
    @ApiModelProperty(value="内容html",name="contentHtml")
    private String contentHtml;
    @NotBlank(message = "作者不能为空")
    @ApiModelProperty(value="作者",name="author")
    private String author;
    @NotNull(message = "发布不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "发布时间",name="publishTime")
    private Date publishTime;
    @NotBlank(message = "摘要不能为空")
    @ApiModelProperty(value = "摘要",name="summary")
    private String summary;
    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题",name="title")
    private String title;
    @ApiModelProperty(value = "机构",name="institution")
    private String institution;
    @NotBlank(message = "封面图片不能为空")
    @ApiModelProperty(value = "封面图片URL",name="imageUrl")
    private String imageUrl;
    @ApiModelProperty(value = "是否置顶",name="topFlg")
    private String topFlg;
    @ApiParam(hidden = true)
    private Integer status;

}

