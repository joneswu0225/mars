package com.jones.mars.controller;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.HuoshanSwapfaceParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.FileUploadService;
import com.jones.mars.util.ObjectUtil;
import com.jones.mars.util.huoshan.ImageSwap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping({"/huoshan"})
@Api(value = "火山接口", tags = {"火山接口"})
public class HuoshanController extends BaseController {

    private static final String SWAPFACE_REL_PATH = "/swapface";

    static {
        File path = new File(CommonConstant.UPLOAD_PATH + "/" + SWAPFACE_REL_PATH);
        if(!path.exists()){
            path.mkdirs();
        }
    }

    @ApiOperation(value = "人像融合", notes = "人像融合")
    @PostMapping(value="/cv/swapface")
    @ResponseBody
    public BaseResponse swapface(
            @NotNull(message = "融合图") @RequestParam(name="sourceImage") @ApiParam(value="融合图",name="sourceImage") MultipartFile sourceFile,
            @NotNull(message = "模板图") @RequestParam(name="templateImage") @ApiParam(value="模板图",name="templateImage") MultipartFile templateFile
    ) throws Exception{
        try {

            String fileName = "swapface" + "_" + sourceFile.getOriginalFilename().split("\\.")[0] + "_" + templateFile.getOriginalFilename().split("\\.")[0] + ".jpg";
            String filePath = CommonConstant.UPLOAD_PATH + "/" + SWAPFACE_REL_PATH + "/" + fileName;
            ImageSwap.getInstance().swapFaceFile(filePath, sourceFile.getInputStream(), templateFile.getInputStream());
            return BaseResponse.builder().data(SWAPFACE_REL_PATH + "/" + fileName).build();
        } catch (Exception e){
            return BaseResponse.builder().code(ErrorCode.INTERNAL_ERROR).data(e.getMessage()).build();
        }
    }

    @ApiOperation(value = "人像融合", notes = "人像融合")
    @PostMapping(value="/cv/swapface/param")
    @ResponseBody
    public BaseResponse swapfaceParam(@RequestBody @ApiParam(required=true) HuoshanSwapfaceParam param) throws Exception{
        String fileName = "swapface" + "_" + param.getImageName().split("\\.")[0] + "_" + param.getTemplateName().split("\\.")[0] + ".jpg";
        String filePath = CommonConstant.UPLOAD_PATH + "/" + SWAPFACE_REL_PATH + "/" + fileName;
        if(!StringUtils.isEmpty(param.getTemplatePath())){
            param.setTemplateBase64(ObjectUtil.getBase64FromInputStream(Files.newInputStream(Paths.get(CommonConstant.UPLOAD_PATH, param.getTemplatePath()))));
        }
        ImageSwap.getInstance().swapFaceFile(filePath, param.getImageBase64(), param.getTemplateBase64());
        return BaseResponse.builder().data(SWAPFACE_REL_PATH + "/" + fileName).build();
    }

}
