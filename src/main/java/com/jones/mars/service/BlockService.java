package com.jones.mars.service;

import com.jones.mars.model.Block;
import com.jones.mars.model.User;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockMapper;
import com.jones.mars.repository.RoleMapper;
import com.jones.mars.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService extends BaseService{

    @Autowired
    private BlockMapper mapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Integer enterpriseId){
        Query query = new Query(Block.builder().enterpriseId(enterpriseId).build());
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse findBlocks(BlockQuery query){
        User user = LoginUtil.getInstance().getUser();
        Integer userType = user.getUserType();
        List<Block> blockList = null;
        if(userType.equals(User.ADMIN)){
            return findByPage(query);
        } else if(userType.equals(User.ENTMANAGER)){
            query.setEnterpriseId(user.getEnterprises().get(0).getId());
            blockList = mapper.findBlockModule(query);
            return BaseResponse.builder().data(blockList).build();
        } else if(userType.equals(User.COMMON)){
            query.setUserId(user.getId());
            blockList = roleMapper.findGrantedBlock(query);
        }
        return BaseResponse.builder().data(blockList).build();
    }

}

