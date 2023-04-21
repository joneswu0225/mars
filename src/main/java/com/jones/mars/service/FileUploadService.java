package com.jones.mars.service;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.*;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.model.query.*;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileUploadService extends BaseService{

    @Value("${app.domain}")
    private String appDomain;
    public static String fileUploadPath;


    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private BlockModuleMapper blockModuleMapper;
    @Autowired
    private BlockClassMapper blockClassMapper;
    @Autowired
    private BlockSceneTypeMapper blockSceneTypeMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HotspotMapper hotspotMapper;
    @Autowired
    private SceneMapper sceneMapper;
    @Autowired
    private AppConstMapper appConstMapper;
    @Autowired
    private BlockContentMapper blockContentMapper;
    @Autowired
    private BlockHotspotMapper blockHotspotMapper;

    @Value("${app.file.path.upload:./files/upload}")
    public void setFileUploadPath(String uploadPath){
        FileUploadService.fileUploadPath = uploadPath;
    }

    public static String fileUploadTempPath;
    @Value("${app.file.path.upload.temp}")
    public void setFileUploadTempPath(String uploadTempPath){
        FileUploadService.fileUploadTempPath = uploadTempPath;
    }

    @PostConstruct
    private void init(){
        File path = Paths.get(fileUploadPath).toFile();
        if(!path.exists()){
            path.mkdirs();
        }
        File tempPath = Paths.get(fileUploadTempPath).toFile();
        if(!tempPath.exists()){
            tempPath.mkdirs();
        }
    }

    @Autowired
    private FileUploadMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse uploadFile(MultipartFile file, String fileName, FileType fileType, Integer relatedId){
        try {
            fileName = StringUtils.isEmpty(fileName) ? file.getOriginalFilename() : fileName;
            int fileSurfixIndex = fileName.lastIndexOf(".");
            int originFileSurfixIndex = file.getOriginalFilename().lastIndexOf(".");
            fileName = fileSurfixIndex > 0 ? fileName.substring(0, fileSurfixIndex) : fileName;
            String fileSurfix = originFileSurfixIndex > 0 ? file.getOriginalFilename().substring(originFileSurfixIndex + 1) : (fileSurfixIndex > 1 ? fileName.substring(fileSurfixIndex + 1) : null);
            fileName = StringUtils.isEmpty(fileSurfix) ? fileName : (fileName + "." + fileSurfix);
            String relPath = fileType.getFilePath(relatedId, fileName);
            Path path = Paths.get(fileUploadPath, relPath);
            path.toFile().mkdirs();
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            FileUpload fileUpload = FileUpload.builder().type(fileType).path(relPath).name(fileName).domain(appDomain).relatedId(relatedId).build();
            if(LoginUtil.getInstance().getUser() != null){
                fileUpload.setUserId(LoginUtil.getInstance().getUser().getId());
            }
            mapper.insert(fileUpload);
            Map<String, String> result = new HashMap<>();
            result.put("path", relPath);
            return BaseResponse.builder().data(result).build();
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return BaseResponse.builder().code(ErrorCode.UPLOAD_FAILED).data(e.getMessage()).build();
        }
    }

    public String exportData(String fileName) throws IOException {
        List<Block> blockList = blockMapper.findAll(BlockQuery.builder().build());
        List<BlockModule> blockModuleList = blockModuleMapper.findAll(ProjectModuleQuery.builder().build());
        List<BlockClass> blockClassList = blockClassMapper.findAll(ProjectClassQuery.builder().build());
        List<BlockSceneType> blockSceneTypeList = blockSceneTypeMapper.findAll(BlockSceneTypeQuery.builder().build());
        List<Project> projectList = projectMapper.findAll(ProjectQuery.builder().build());
        List<Enterprise> enterpriseList = enterpriseMapper.findAll(EnterpriseQuery.builder().build());
        List<User> userList = userMapper.findAll(UserQuery.builder().build());
        List<Hotspot> hotspotList = hotspotMapper.findAllByQuery(HotspotQuery.builder().build());
        List<Scene> sceneList = sceneMapper.findAll(SceneQuery.builder().build());
        List<AppConst> appConstList = appConstMapper.findAll(AppConstQuery.builder().build());
        Map<Integer, Block> blockMap = blockList.parallelStream().collect(Collectors.toMap(Block::getId, Block -> Block));
        blockContentMapper.findAll(BlockContentQuery.builder().build()).forEach(blockContent -> {
            if (blockMap.containsKey(blockContent.getBlockId())){
                blockMap.get(blockContent.getBlockId()).getBlockContentList().add(blockContent);
            }
        });
        blockHotspotMapper.findAll(BlockContentQuery.builder().build()).forEach(blockHotspot -> {
            if (blockMap.containsKey(blockHotspot.getBlockId())){
                blockMap.get(blockHotspot.getBlockId()).getBlockHotspotList().add(blockHotspot);
            }
        });
        Map<String, Object> data = new HashMap<>();
        data.put("enterprise", enterpriseList);
        data.put("blockModule", blockModuleList);
        data.put("blockClass", blockClassList);
        data.put("blockSceneType", blockSceneTypeList);
        data.put("block", blockList);
        data.put("project", projectList);
        data.put("scene", sceneList);
        data.put("hotspot", hotspotList);
        data.put("user", userList);
        data.put("appConst", appConstList);
        String result = JSONObject.toJSONString(data);
        Path path = Paths.get(fileUploadPath, "..", StringUtils.isEmpty(fileName) ? "data_all.json" : fileName);
        try(FileOutputStream outputStream = new FileOutputStream(path.toFile())){
            outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return path.toAbsolutePath().toString();
    }


    public String getFileUploadPath(){
        return fileUploadPath;
    }

    public String getFileTypeByStream(byte[] b) {
        String filetypeHex = String.valueOf(getFileHexString(b));
        Iterator<Map.Entry<String, String>> entryiterator = FILE_TYPE_MAP
                .entrySet().iterator();
        while (entryiterator.hasNext()) {
            Map.Entry<String, String> entry = entryiterator.next();
            String fileTypeHexValue = entry.getKey().toUpperCase();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String getFileHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static final Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
    static {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); //PNG (png)
        FILE_TYPE_MAP.put("47494638396126026f01", "gif"); //GIF (gif)
        FILE_TYPE_MAP.put("49492a00227105008037", "tif"); //TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); //16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); //24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg"); //CAD (dwg)
        FILE_TYPE_MAP.put("3c21444f435459504520", "html"); //HTML (html)
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm"); //HTM (htm)
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css"); //css
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js"); //js
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", "psd"); //Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf"); //AdobeAcrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); //flv与f4v相同
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg"); //
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同
        FILE_TYPE_MAP.put("52494646e27807005741", "wav"); //Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid"); //MIDI (mid)
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");//可执行文件
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");//jsp文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");//MF文件
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");//xml文件
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");//xml文件
        FILE_TYPE_MAP.put("7061636b616765207765", "java");//java文件
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");//bat文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");//gz文件
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");//bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");//bat文件
        FILE_TYPE_MAP.put("49545346030000006000", "chm");//bat文件
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");//bat文件
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");//docx文件
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");


        FILE_TYPE_MAP.put("6D6F6F76", "mov"); //Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", "wpd"); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", "pst"); //Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf"); //Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", "pwl"); //Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", "ram"); //Real Audio (ram)
    }

}

