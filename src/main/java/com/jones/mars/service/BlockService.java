package com.jones.mars.service;

import com.jones.mars.model.Block;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.User;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.model.query.EnterpriseQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

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
//        if(user == null){
//            user = userMapper.findOne(11);
//        }
        Integer userType = user.getUserType();
        List<Block> blockList = null;
        if(userType.equals(User.ADMIN)){
            return findByPage(query);
        } else if(userType.equals(User.ENTMANAGER)){
            blockList = mapper.findBlockModule(query);
            return BaseResponse.builder().data(blockList).build();
        } else if(userType.equals(User.COMMON)){
            query.setUserId(user.getId());
            blockList = roleMapper.findGrantedBlock(query);
        }
        return BaseResponse.builder().data(blockList).build();
    }

}

