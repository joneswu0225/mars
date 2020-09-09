package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.exception.InternalException;
import com.jones.mars.model.Block;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.User;
import com.jones.mars.model.param.UserLoginParam;
import com.jones.mars.model.param.UserProfileParam;
import com.jones.mars.model.param.UserWXLoginParam;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.model.query.EnterpriseQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.query.UserQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User>{

    @Autowired
    private UserMapper mapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private BlockMapper blockMapper;

    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }

    private boolean exists(String mobile){
        Integer count = mapper.findCount(UserQuery.builder().mobile(mobile).build());
        return count > 0;
    }


    public BaseResponse personal(Integer userId){
        // base info
        User user = mapper.findOne(userId);
        // enterprise
        if(user.getUserType().equals(User.COMMON)){
            List<Enterprise> enterprises = enterpriseMapper.findUserEnterprise(EnterpriseQuery.builder().userId(userId).build());
            user.setEnterprises(enterprises);
        } else if(user.getUserType().equals(User.ENTMANAGER)) {
            List<Enterprise> enterprises = enterpriseMapper.findList(EnterpriseQuery.builder().managerId(userId).build());
            user.setEnterprises(enterprises);
        }
        // block
        List<Block> userBlockPermission = blockMapper.findBlockUserPermission(BlockQuery.builder().userId(userId).build());
        user.setBlocks(userBlockPermission);
        return BaseResponse.builder().data(user).build();
    }

    /**
     * 手机号查重
     * @param mobile
     * @return
     */
    public BaseResponse mobileExists(String mobile){
        Map<String, Object> result =new HashMap<>();
        result.put("exists", true);
        if(exists(mobile)){
            return BaseResponse.builder().code(ErrorCode.REGIST_MOBILE_EXISTS).data(result).build();
        }else {
            result.put("exists", false);
            return BaseResponse.builder().data(result).build();
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(User user){
        if(!exists(user.getMobile())){
            if(!StringUtils.isEmpty(user.getPassword())){
                // TODO 对密码加密
                user.setPassword(user.getPassword());
            }
            if(user.getUserType() == null){
                user.setUserType(User.COMMON);
            }
            mapper.insert(user);
            mapper.insertProfile(user);
            return BaseResponse.builder().data(user.getId()).build();
        } else {
            return BaseResponse.builder().code(ErrorCode.REGIST_MOBILE_EXISTS).build();
        }
    }

    /**
     * 获取验证码
     * TODO 对接短信系统
     * @param mobile
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getVerifyCode(String mobile){
        String verifyCode = RandomString.generateVerifyCode();
        AliMnsSender.sendMns(mobile, verifyCode);
        return BaseResponse.builder().build();
/*
        List<User> users = mapper.findList(UserQuery.builder().mobile(mobile).build());
        if(users.size() == 1){
            User user_db = users.get(0);
            User user_update = User.builder().verifyCode(verifyCode).build();
            user_update.setId(user_db.getId());
            mapper.update(user_update);
            AliMnsSender.sendMns(mobile, verifyCode);
//            Map<String, String> resp = new HashMap<>();
//            resp.put("verifyCode", verifyCode);
            return BaseResponse.builder().build();
        } else if(users.size() < 1){
            return BaseResponse.builder().code(ErrorCode.LOGIN_MOBILE_NOTEXISTS).build();
        } else {
            throw new InternalException("手机号重复");
        }*/
    }

    /**
     * 重置密码
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse resetPassword(User param){
        List<User> users = mapper.findList(new Query(param));
        if(users.size() == 1){
            User user_db = users.get(0);
            User user_update = User.builder().password(param.getPassword()).build();
            user_update.setId(user_db.getId());
            mapper.update(user_update);
            return BaseResponse.builder().data(user_update.getId()).build();
        } else if(users.size() < 1){
            return BaseResponse.builder().code(ErrorCode.LOGIN_MOBILE_NOTEXISTS).build();
        } else {
            throw new InternalException("手机号重复");
        }
    }

    /**
     * 用户登录
     * @param userParam
     * @return
     */
    public BaseResponse doLogin(UserLoginParam userParam) {
        UserQuery.UserQueryBuilder builder = UserQuery.builder().mobile(userParam.getMobile());
        if(StringUtils.isEmpty(userParam.getVerifyCode()) & StringUtils.isEmpty(userParam.getPassword())) {
            return BaseResponse.builder().code(ErrorCode.VALIDATION_FAILED).message("验证码和密码不能同时为空").build();
        } else if (!StringUtils.isEmpty(userParam.getVerifyCode())){
            builder.verifyCode(userParam.getVerifyCode()).password(null);
        } else {
            builder.password(userParam.getPassword()).verifyCode(null);
        }
        List<User> users = mapper.findList(builder.build());
        if(users.size() == 1){
            Date now = new Date();
            User user_db = users.get(0);
            user_db.setLastLoginTime(now);
            User user_update = User.builder().lastLoginTime(now).build();
            user_update.setId(user_db.getId());
            mapper.update(user_update);
            Map<String, Object> result = new HashMap<>();
            String authorization = "Basic" + UuidUtil.generate().toUpperCase();
            result.put("id", user_db.getId());
            result.put("avatar", user_db.getAvatar());
            // 返回用户基本信息
            // 从enterprise_user表中查询所有的企业
            // 如果是管理员则不返回
            if(user_db.getUserType().equals(User.COMMON)){
                List<Enterprise> enterprises = enterpriseMapper.findUserEnterprise(EnterpriseQuery.builder().userId(user_db.getId()).build());
                result.put("enterprises", enterprises);
                user_db.setEnterprises(enterprises);
            } else if(user_db.getUserType().equals(User.ENTMANAGER)) {
                List<Enterprise> enterprises = enterpriseMapper.findList(EnterpriseQuery.builder().managerId(user_db.getId()).build());
                result.put("enterprises", enterprises);
                user_db.setEnterprises(enterprises);
            }
//            if(user_db.getUserType().equals(User.COMMON)){
//                List<Block> roleList = roleMapper.findGrantedBlock(user_db.getId());
//                result.put("roles", roleList);
//            }
            result.put("expireTime", new Date(now.getTime() + LoginUtil.COOKIE_MAX_INACTIVE_INTERVAL));
            result.put("userType", user_db.getUserType());
            LoginUtil.getInstance().setUser(authorization, user_db);
            result.put("authorization", user_db.getAuth());
            return BaseResponse.builder().data(result).build();
        } else if(users.size() < 1){
            return BaseResponse.builder().code(ErrorCode.LOGIN_FAIL).build();
        } else {
            throw new InternalException("手机及验证码重复");
        }
    }

    public BaseResponse doWxLogin(UserWXLoginParam param){
        Map<String, String> wechatInfo = WechatApiUtil.getUserInfo(param.getCode(),param.getEncryptedData(), param.getIv());
        if(wechatInfo == null) {
            return BaseResponse.builder().code(ErrorCode.WECHAT_LOGIN_VERIFY_FAIL).build();
        }
        User user = mapper.findOneByMobile(wechatInfo.get("mobile"));
        if(user == null){
            mapper.insert(User.builder().mobile(wechatInfo.get("mobile")).openid(wechatInfo.get("openid")).unionid(wechatInfo.get("unionid")));
        } else if (StringUtils.isEmpty(user.getOpenid()) || StringUtils.isEmpty(user.getUnionid())) {
            mapper.updateByMobile(wechatInfo);
        }
        if(user == null || StringUtils.isEmpty(user.getPassword())){
            return BaseResponse.builder().code(ErrorCode.WECHAT_NO_PASSWD).build();
        } else {
            return doLogin(UserLoginParam.builder().mobile(user.getMobile()).password(user.getPassword()).build());
        }
    }

    public BaseResponse wxUpdatePassword(UserWXLoginParam param){
        Map<String, String> wechatInfo = WechatApiUtil.getUserInfo(param.getCode(),param.getEncryptedData(), param.getIv());
        if(wechatInfo == null) {
            return BaseResponse.builder().code(ErrorCode.WECHAT_LOGIN_VERIFY_FAIL).build();
        }
        wechatInfo.put("password", param.getPassword());
        mapper.updateByMobile(wechatInfo);
        return BaseResponse.builder().code(ErrorCode.WECHAT_NO_PASSWD).build();
    }


    public BaseResponse updateProfile(UserProfileParam param){
        mapper.updateProfile(param);
        return BaseResponse.builder().build();
    }

}

