package com.jones.mars.service;

import com.jones.mars.model.*;
import com.jones.mars.model.param.BlockHotspotParam;
import com.jones.mars.model.query.*;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
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
    private BlockHotspotMapper blockHotspotMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Integer enterpriseId){
        Query query = new Query(Block.builder().enterpriseId(enterpriseId).build());
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse findAuthBlocks(BlockQuery query){
        User user = LoginUtil.getInstance().getUser();
//        if(user == null){
//            user = userMapper.findOne(18);
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

    public BaseResponse findBlocks(BlockQuery query){
        return findByPage(query);
    }


    public BaseResponse findBlockById(Integer blockId){
        Block block = mapper.findOne(blockId);
        List<BlockHotspot> blockHotspotList = blockHotspotMapper.findAll(BlockHotspotQuery.builder().blockId(blockId).build());
        block.setBlockHotspotList(blockHotspotList);
        return BaseResponse.builder().data(block).build();
    }

    /**
     * 获取企业下二级分类的共建人信息
     * @param classId
     * @return
     */
    public BaseResponse findClassPartner(Integer classId){
        return BaseResponse.builder().data(rolePermissionMapper.findGrantedUserByClassId(RolePermissionQuery.builder().classId(classId).operation(RolePermission.CREATE).build())).build();
    }

    public BaseResponse insertBlockHotspot(BlockHotspotParam param) {
        Integer maxSeq = blockHotspotMapper.findMaxSeqByBlockId(param.getBlockId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        blockHotspotMapper.insert(param);
        return BaseResponse.builder().data(param.getId()).build();
    }

    public BaseResponse deleteBlockHotspot(Integer id){
        blockHotspotMapper.delete(id);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateblockHotspot(BlockHotspotParam param) {
        blockHotspotMapper.update(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateblockHotspotSeq(BlockHotspotParam param) {
        blockHotspotMapper.updateBlockHotspotSeq(param);
        return BaseResponse.builder().build();
    }
}

