package com.jones.mars.controller;

import com.jones.mars.model.DeployLicense;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.model.param.DeployLicenseParam;
import com.jones.mars.model.param.EnterpriseParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.DeployLicenseService;
import com.jones.mars.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/deployLicense")
@Slf4j
@Api(value = "Deploy License", tags = {"Deploy License"})
public class DeployLicenseController extends BaseController {
    @Autowired
    private DeployLicenseService service;

    @PostMapping(value="/generate")
    public ResponseEntity fileUpload(@RequestBody @ApiParam(required=true) DeployLicenseParam param) throws Exception{
        DeployLicense deployLicense = service.save(param);
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=license.key_tab");
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        String result = deployLicense.getKeyPrivate() + "\n" + deployLicense.getContentEnc();
        ResponseEntity<byte[]> response=new ResponseEntity(IOUtils.toByteArray(IOUtils.toInputStream(result)), headers, statusCode);
        return response;
    }

}
