package com.jones.mars.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jones.mars.model.*;
import com.jones.mars.model.param.DeployLicenseParam;
import com.jones.mars.model.query.*;
import com.jones.mars.repository.*;
import com.jones.mars.util.AESUtil;
import com.jones.mars.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeployLicenseService extends BaseService{

    @Autowired
    private DeployLicenseMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

/*    public DeployLicense save(DeployLicenseParam param) throws Exception {
        String content = String.join(";", new String[]{param.getDiskSeries(), param.getDirectory(),
                DateUtil.dateToDateTimeStr(param.getStartTime()), DateUtil.dateToDateTimeStr(param.getExpireTime())});

        Map<Integer, String> keyMap = RsaUtil.genKeyPair();
        String encryptStr = RsaUtil.encrypt(content, keyMap.get(0));
        DeployLicense deployLicense = DeployLicense.builder().name(param.getName()).detail(param.getDetail())
                .diskSeries(param.getDiskSeries()).direcotry(param.getDirectory())
                        .startTime(param.getStartTime()).expireTime(param.getExpireTime())
                        .keyPublic(keyMap.get(0)).keyPrivate(keyMap.get(1))
                        .contentOri(content).contentEnc(encryptStr).build();
        mapper.insert(deployLicense);
        return deployLicense;
    }*/
    public DeployLicense save(DeployLicenseParam param) throws Exception {
//        List<DeployLicense> licenseList = mapper.findAll(DeployLicenseQuery.builder().diskSeries(param.getDiskSeries()).build());
        DeployLicense deployLicense = DeployLicense.builder().name(param.getName()).detail(param.getDetail())
                    .diskSeries(param.getDiskSeries()).path(param.getPath())
                    .startDate(param.getStartDate()).expireDate(param.getExpireDate())
                    .build();
        mapper.insert(deployLicense);
        List<DeployLicense> licenseList = mapper.findAll(DeployLicenseQuery.builder().diskSeries(param.getDiskSeries()).path(param.getPath()).build());
        deployLicense = licenseList.get(0);
        String diskId = deployLicense.getId().toString();
        Integer disk = Integer.valueOf(diskId.substring(diskId.length() - 8));
        String key = String.format("%08d", disk) + deployLicense.getDiskSeries().replace("-","");
        StringJoiner sj = new StringJoiner(";");
        String content = sj.add(deployLicense.getDiskSeries()).add(deployLicense.getPath())
                .add(DateUtil.dateToDateStr(deployLicense.getStartDate()))
                .add(DateUtil.dateToDateStr(deployLicense.getExpireDate())).toString();
        String encryped = AESUtil.AESencrypt(key, content);
        deployLicense.setKey(key);
        deployLicense.setContentOri(content);
        deployLicense.setContentEnc(encryped);
        mapper.update(deployLicense);
        return deployLicense;
    }
    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private BlockTypeMapper blockTypeMapper;
    @Autowired
    private SceneMapper sceneMapper;
    @Autowired
    private BlockContentMapper blockContentMapper;
    @Autowired
    private BlockSceneTypeMapper blockSceneTypeMapper;
    @Autowired
    private BlockAttachmentMapper blockAttachmentMapper;
    @Autowired
    private BlockAttachmentContentMapper blockAttachmentContentMapper;
    @Autowired
    private BlockExamineMapper blockExamineMapper;
    @Autowired
    private BlockExamineContentMapper blockExamineContentMapper;
    @Autowired
    private BlockTourSpotMapper blockTourSpotMapper;
    public String getLocalDbInfo(Long blockId){
        Block block = blockMapper.findOne(blockId);
        Long enterpriseId = block.getEnterpriseId();
        Enterprise enterprise = enterpriseMapper.findOne(enterpriseId);
        List<BlockType> blockTypeList = blockTypeMapper.findAll(BlockTypeQuery.builder().enterpriseId(enterpriseId).build());
        List<BlockSceneType> blockSceneTypeList = blockSceneTypeMapper.findAll(BlockSceneTypeQuery.builder().blockId(blockId).build());
        List<Scene> sceneList = sceneMapper.findAll(SceneQuery.builder().blockId(blockId).build());
        List<BlockContent> blockContentList = blockContentMapper.findAll(BlockContentQuery.builder().blockId(blockId).build());
        List<BlockAttachment> blockAttachmentList = blockAttachmentMapper.findAll(BlockAttachmentQuery.builder().blockId(blockId).build());
        List<BlockAttachmentContent> blockAttachmentContentList = blockAttachmentContentMapper.findAll(BlockAttachmentContentQuery.builder().blockId(blockId).build());
        List<BlockExamine> blockExamineList = blockExamineMapper.findAll(BlockExamineQuery.builder().blockId(blockId).build());
        List<BlockExamineContent> blockExamineContentList = blockExamineContentMapper.findAll(BlockExamineContentQuery.builder().blockId(blockId).build());
        List<BlockTourSpot> blockTourSpotList = blockTourSpotMapper.findAll(BlockTourSpotQuery.builder().blockIds(blockTypeList.stream().map(p->p.getId()).collect(Collectors.toList())).build());
        Map<String, Object> tableData = new HashMap<>();
        tableData.put("block", Arrays.asList(block));
        tableData.put("enterprise", Arrays.asList(enterprise));
        tableData.put("block_type", blockTypeList);
        tableData.put("block_scene_type", blockSceneTypeList);
        tableData.put("scene", sceneList);
        tableData.put("block_content", blockContentList);
        tableData.put("block_attachment", blockAttachmentList);
        tableData.put("block_attachment_content", blockAttachmentContentList);
        tableData.put("block_exmine", blockExamineList);
        tableData.put("block_examine_content", blockExamineContentList);
        tableData.put("block_tour_spot", blockTourSpotList);
        String result = JSONObject.toJSONString(tableData);
        return result;
    }

    public String getEncryptedDbInfo(String key, String content) throws Exception {
        if(key != null){
            return AESUtil.AESencrypt(key, content);
        }
        return content;
    }

    public String getEncryptedDbInfo(String key, Long blockId) throws Exception {
        return getEncryptedDbInfo(key, getLocalDbInfo(blockId));
    }

}

