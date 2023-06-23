package com.jones.mars.service;

import com.jones.mars.model.BlockExamine;
import com.jones.mars.model.query.BlockAttachmentQuery;
import com.jones.mars.model.query.BlockExamineQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockExamineContentMapper;
import com.jones.mars.repository.BlockExamineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockExamineService extends BaseService{

    @Autowired
    private BlockExamineMapper mapper;
    @Autowired
    private BlockExamineContentMapper blockExamineContentMapper;
  

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Integer blockId){
        Query query = BlockExamineQuery.builder().blockId(blockId).build();
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse findBlockExamines(BlockExamineQuery query){
        return findByPage(query);
    }

    public BaseResponse findBlockExamineById(Integer examineId){
        BlockExamine examine = mapper.findOne(examineId);
        examine.setBlockExamineContentList(blockExamineContentMapper.findByExamineId(examine.getId()));
        return BaseResponse.builder().data(examine).build();
    }

    public BaseResponse deleteExamine(Integer examineId){
        super.delete(examineId);
        blockExamineContentMapper.deleteByExamineId(examineId);
        return BaseResponse.builder().build();
    }

}

