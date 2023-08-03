package com.jones.mars.service;

import com.jones.mars.model.AppConst;
import com.jones.mars.model.DeployLicense;
import com.jones.mars.model.Project;
import com.jones.mars.model.param.AppConstParam;
import com.jones.mars.model.param.DeployLicenseParam;
import com.jones.mars.model.query.AppConstQuery;
import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.AppConstMapper;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.DeployLicenseMapper;
import com.jones.mars.repository.ProjectMapper;
import com.jones.mars.util.DateUtil;
import com.jones.mars.util.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeployLicenseService extends BaseService{

    @Autowired
    private DeployLicenseMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public DeployLicense save(DeployLicenseParam param) throws Exception {
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
    }

}

