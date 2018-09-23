package com.jones.mars.service;

import com.jones.mars.model.Block;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockMapper;
import com.jones.mars.repository.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnterpriseService extends BaseService{
    @Autowired
    private EnterpriseMapper mapper;
    @Autowired
    private BlockMapper blockMapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    private Collection<Enterprise> plateformEnterprise;

    @PostConstruct
    private void init(){
        refreshPlateformEnterprise();
    }

    private void refreshPlateformEnterprise(){
        Query query = new Query(Enterprise.builder().plateformFlg(Enterprise.PLATEFROM).build());
        Map<Integer, Enterprise> plateformEnterpriseMap = mapper.findAllName(query).stream()
                .collect(Collectors.toMap(Enterprise::getId, p->p));
        List<Block> blockList = blockMapper.findList(new Query(Block.builder().plateformFlg(Block.PLATEFROM).build()));
        blockList.forEach(p->{
            if(plateformEnterpriseMap.containsKey(p.getEnterpriseId())){
                plateformEnterpriseMap.get(p.getEnterpriseId()).getBlockList().add(p);
            }
        });
        this.plateformEnterprise = plateformEnterpriseMap.values();
    }

    public BaseResponse plateformEnterprise(){
        return BaseResponse.builder().data(this.plateformEnterprise).build();
    }

}

