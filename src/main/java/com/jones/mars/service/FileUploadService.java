package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.FileUploadMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FileUploadService extends BaseService{

    @Value("${app.file.upload.path}")
    private String fileUploadPath;

    @PostConstruct
    private void init(){
        File path = Paths.get(fileUploadPath).toFile();
        if(!path.exists()){
            path.mkdirs();
        }
    }

    @Autowired
    private FileUploadMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse uploadFile(MultipartFile file, String fileName, FileType fileType, Integer relatedId){
        String relPath = fileType.getFilePath(relatedId, StringUtils.isEmpty(fileName) ? file.getName() : fileName);
        Path path = Paths.get(fileUploadPath, relPath);
        path.toFile().mkdirs();
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Map<String, String> result = new HashMap<>();
            result.put("path", relPath);
            return BaseResponse.builder().data(result).build();
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return BaseResponse.builder().code(ErrorCode.UPLOAD_FAILED).build();
        }
    }

    public String getFileUploadPath(){
        return fileUploadPath;
    }

}

