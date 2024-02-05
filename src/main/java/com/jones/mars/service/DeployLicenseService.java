package com.jones.mars.service;

import com.jones.mars.model.DeployLicense;
import com.jones.mars.model.param.DeployLicenseParam;
import com.jones.mars.model.query.DeployLicenseQuery;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.DeployLicenseMapper;
import com.jones.mars.util.AESUtil;
import com.jones.mars.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;

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
        List<DeployLicense> licenseList = mapper.findAll(DeployLicenseQuery.builder().diskSeries(param.getDiskSeries()).build());
        DeployLicense deployLicense;
        if(licenseList.size() > 0){
            deployLicense = licenseList.get(0);
        } else {
            deployLicense = DeployLicense.builder().name(param.getName()).detail(param.getDetail())
                    .diskSeries(param.getDiskSeries()).path(param.getPath())
                    .startDate(param.getStartDate()).expireDate(param.getExpireDate())
                    .build();
            mapper.insert(deployLicense);
        }

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
//        DeployLicense updateParam = DeployLicense.builder().id(deployLicense.getId()).key(key).contentOri(content).contentEnc(encryped).build();
        mapper.update(deployLicense);
        return deployLicense;
    }

}

