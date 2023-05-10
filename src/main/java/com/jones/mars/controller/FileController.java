package com.jones.mars.controller;

import com.jones.mars.exception.RequestException;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
@Slf4j
@Api(value = "上传下载", tags = {"上传下载"})
public class FileController extends BaseController {
    @Autowired
    private FileUploadService service;

    @PostMapping(value="/upload")
    public BaseResponse fileUpload(
            @NotNull(message = "上传文件不能为空") @RequestParam(name="file") @ApiParam(value="上传文件",name="file") MultipartFile file,
            @RequestParam(name="fileName", required = false) @ApiParam(value="文件名称",name="fileName") String fileName,
            @RequestParam(name="relatedId", required = false) @ApiParam(value="关联内容的id， 如上传名片为userId, 上传企业logo为enterpirseId",name="relatedId") Integer relatedId,
            @RequestParam(name="fileType") @ApiParam(value="文件类型",name="fileType") FileType fileType) throws Exception{
        BaseResponse resp = service.uploadFile(file, fileName, fileType, relatedId);
        return resp;
    }

    @GetMapping("/download")
    public ResponseEntity fileDownLoad(@RequestParam("path") String path) throws Exception{
//        String fileName = path.split("_")[1];http://localhost:9900/user/1
//        fileName=new String(fileName.getBytes("gbk"),"iso8859-1");//防止中文乱码
        String parentPath = CommonConstant.UPLOAD_PATH;
        Path realPath = Paths.get(parentPath, path).normalize();
        if(!realPath.startsWith(parentPath)){
            throw new RequestException("非法路径");
        }
        if(!realPath.toFile().exists()){
            throw new RequestException("文件不存在");
        }
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename="+realPath.getFileName().toString());
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response=new ResponseEntity(IOUtils.toByteArray(realPath.toUri()), headers, statusCode);
        return response;
    }

    @GetMapping("/exportData")
    public BaseResponse exportData(@RequestParam("fileName") String fileName) throws Exception{
        return BaseResponse.builder().data(service.exportData(fileName)).build();
    }

}
