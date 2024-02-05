package com.jones.mars.service;

import com.jones.mars.model.BlockImage;
import com.jones.mars.model.BlockSceneType;
import com.jones.mars.model.param.BlockExamineContentParam;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import com.jones.mars.model.param.BlockSceneTypeParam;
import com.jones.mars.model.param.BlockSceneTypeSeqParam;
import com.jones.mars.model.query.BlockSceneTypeQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.BlockImageMapper;
import com.jones.mars.repository.BlockSceneTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlockSceneTypeService extends BaseService{

    @Autowired
    private BlockSceneTypeMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse insertSceneType(BlockSceneTypeParam param){
        Integer maxSeq = mapper.findMaxSeqByBlockId(param.getBlockId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        BlockSceneType sceneType = BlockSceneType.builder().blockId(param.getBlockId()).name(param.getName()).detail(param.getDetail()).seq(maxSeq).build();
        mapper.insert(sceneType);
        Map<String, Long> map = new HashMap<>();
        map.put("id", sceneType.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse allName(Long blockId){
        List<BlockSceneType> list = mapper.findAllName(BlockSceneTypeQuery.builder().blockId(blockId).build());
        return BaseResponse.builder().data(list).build();
    }
    public BaseResponse findSceneTypeProjectScene(Long blockId){
        List<BlockSceneType> list = mapper.findSceneTypeProjectScene(BlockSceneTypeQuery.builder().blockId(blockId).build());
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse updateBlockSceneTypeSeq(BlockSceneTypeSeqParam param){
        mapper.updateBlockSceneTypeSeq(param);
        return BaseResponse.builder().build();
    }

}

