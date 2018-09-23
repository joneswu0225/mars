package com.jones.mars.controller;

import com.jones.mars.model.param.UploadParam;
import com.jones.mars.object.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
@Slf4j
@Api(value = "上传下载", tags = {"上传下载"})
public class FileController extends BaseController {

    @Value("${app.file.upload.path}")
    private String fileUploadPath;

    @PostConstruct
    private void init(){
        File path = Paths.get(fileUploadPath).toFile();
        if(!path.exists()){
            path.mkdirs();  
        }
    }
    @PostMapping(value="/upload")
    public BaseResponse fileUpload(@RequestBody UploadParam param) throws Exception{
        // TODO 根据 FileType 生成特定的path
        String relPath = String.valueOf(System.currentTimeMillis()) + "_" + (StringUtils.isEmpty(param.getFileName()) ? param.getFile().getName() : param.getFileName());
        Path path = Paths.get(fileUploadPath, relPath);
        Files.copy(param.getFile().getInputStream(), path);
        Map<String, String> result = new HashMap<>();
        result.put("path", relPath);
        return BaseResponse.builder().data(relPath).build();
    }
    @GetMapping("/download")
    public ResponseEntity fileDownLoad(@RequestParam("path") String path) throws Exception{
        String fileName = path.split("_")[1];
        fileName=new String(fileName.getBytes("gbk"),"iso8859-1");//防止中文乱码
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename="+fileName);
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response=new ResponseEntity(IOUtils.toByteArray(URI.create(fileUploadPath + File.separator + path)), headers, statusCode);
        return response;
    }

}
