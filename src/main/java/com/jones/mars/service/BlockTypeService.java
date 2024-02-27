package com.jones.mars.service;

import com.jones.mars.model.BlockType;
import com.jones.mars.model.param.BlockTypeParam;
import com.jones.mars.model.param.BlockTypeSeqParam;
import com.jones.mars.model.query.BlockTypeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BlockTypeMapper;
import com.jones.mars.repository.BlockTypeMapper;
import com.jones.mars.repository.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlockTypeService extends BaseService{

    @Autowired
    private BlockTypeMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse insertBlockType(BlockTypeParam param){
        Integer maxSeq = mapper.findMaxSeqByEnterpriseId(param.getEnterpriseId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        BlockType sceneType = BlockType.builder().enterpriseId(param.getEnterpriseId()).name(param.getName()).detail(param.getDetail()).seq(maxSeq).build();
        mapper.insert(sceneType);
        Map<String, String> map = new HashMap<>();
        map.put("id", sceneType.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse allName(String enterpriseId){
        List<BlockType> list = mapper.findAllName(BlockTypeQuery.builder().enterpriseId(enterpriseId).build());
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse updateBlockTypeSeq(BlockTypeSeqParam param){
        mapper.updateBlockTypeSeq(param);
        return BaseResponse.builder().build();
    }

}

