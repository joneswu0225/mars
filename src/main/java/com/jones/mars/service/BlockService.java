package com.jones.mars.service;

import com.jones.mars.model.Block;
import com.jones.mars.model.EnterpriseUser;
import com.jones.mars.model.RolePermission;
import com.jones.mars.model.User;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.model.query.EnterpriseUserQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.query.RolePermissionQuery;
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
    private EnterpriseUserMapper enterpriseUserMapper;
    @Autowired
    private BlockContentMapper blockContentMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(String enterpriseId){
        Query query = new Query(Block.builder().enterpriseId(enterpriseId).build());
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse findOneByCode(String code){
        Block block = mapper.findOneByCode(code);
        block.setBlockContentList(blockContentMapper.findByBlockId(block.getId()));
        return BaseResponse.builder().data(block).build();
    }

    public BaseResponse findAuthBlocks(BlockQuery query){
        User user = LoginUtil.getInstance().getUser();
//        if(user == null){
//            user = userMapper.findOne(18);
//        }
        Integer userType = user.getUserType();
        List<Block> blockList = null;
//        if(userType.equals(User.ADMIN)){
//            return findByPage(query);
//        } else if(userType.equals(User.ENTMANAGER)){
//            blockList = mapper.findBlockModule(query);
//            return BaseResponse.builder().data(blockList).build();
//        } else if(userType.equals(User.COMMON)){
//            query.setUserId(user.getId());
//            blockList = roleMapper.findGrantedBlock(query);
//        }
        if(userType.equals(User.ADMIN)){
            return findAll(query);
        } else {
            List<EnterpriseUser> enterpriseUserList = enterpriseUserMapper.findList(EnterpriseUserQuery.builder().enterpriseId(query.getEnterpriseId()).userId(user.getId()).build());
            if (enterpriseUserList.size() > 0){
                EnterpriseUser enterpriseUser = enterpriseUserList.get(0);
                if(enterpriseUser.getManagerFlg().equals(EnterpriseUser.ENTERPRISE_MANAGER)){
                    return findAll(query);
                } else {
                    blockList = mapper.findUserBlock(BlockQuery.builder().enterpriseId(query.getEnterpriseId()).userId(user.getId()).build());
                }
            }
        }
        return BaseResponse.builder().data(blockList).build();
    }

    public BaseResponse findBlocks(BlockQuery query){
        return findByPage(query);
    }


    public BaseResponse findBlockById(String blockId){
        Block block = mapper.findOne(blockId);
        block.setBlockContentList(blockContentMapper.findByBlockId(block.getId()));
        return BaseResponse.builder().data(block).build();
    }

    /**
     * 获取企业下二级分类的共建人信息
     * @param classId
     * @return
     */
    public BaseResponse findClassPartner(String classId){
        return BaseResponse.builder().data(rolePermissionMapper.findGrantedUserByClassId(RolePermissionQuery.builder().classId(classId).operation(RolePermission.CREATE).build())).build();
    }

}

