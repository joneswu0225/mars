package com.jones.mars.service;

import com.jones.mars.config.LoginUser;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.exception.InternalException;
import com.jones.mars.model.Block;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.EnterpriseUser;
import com.jones.mars.model.User;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.*;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.model.query.EnterpriseQuery;
import com.jones.mars.model.query.EnterpriseUserQuery;
import com.jones.mars.model.query.UserQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService extends BaseService<User>{

    @Autowired
    private UserMapper mapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private EnterpriseUserMapper enterpriseUserMapper;
    @Autowired
    private BlockMapper blockMapper;

    @Override
    public CommonMapper getMapper() {
        return this.mapper;
    }

    private boolean exists(String mobile){
        Integer count = mapper.findCount(UserQuery.builder().mobile(mobile).build());
        return count > 0;
    }


    public BaseResponse personal(String userId){
        // base info
        User user = mapper.findOne(userId);
        // enterprise
        if(!user.getUserType().equals(User.ADMIN)){
            List<EnterpriseUser> enterprises = enterpriseUserMapper.findAll(EnterpriseUserQuery.builder().userId(userId).build());
            user.setEnterprises(enterprises);
            List<Block> userBlock = blockMapper.findUserBlock(BlockQuery.builder().userId(userId).build());
            user.setBlocks(userBlock);
        }
        // block
//        List<Block> userBlockPermission = blockMapper.findBlockUserPermission(BlockQuery.builder().userId(userId).build());
//        user.setBlocks(userBlockPermission);
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
            String userId = user.getId();
            user.setId(null);
            user.setUserId(userId);
            user.setSgname("新用户" + user.getMobile().substring(user.getMobile().length()-4));
            mapper.insertProfile(user);
            return BaseResponse.builder().data(userId).build();
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
//        AliMnsSender.sendMns(mobile, verifyCode);
//        return BaseResponse.builder().build();
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
        }
    }

    /**
     * 重置密码
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse resetPassword(UserPasswordRestParam param){
        User user = mapper.findOneByMobile(param.getMobile());
        if(user == null){
            return BaseResponse.builder().code(ErrorCode.LOGIN_MOBILE_NOTEXISTS).build();
        }
        if(param.getVerifyCode() != null && param.getVerifyCode().equals(user.getVerifyCode())) {
            User user_update = User.builder().password(param.getPassword()).build();
            user_update.setId(user.getId());
            mapper.update(user_update);
            return BaseResponse.builder().data(user_update.getId()).build();
        } if(param.getPasswordOld() != null && param.getPasswordOld().equals(user.getPassword())) {
            User user_update = User.builder().password(param.getPassword()).build();
            user_update.setId(user.getId());
            mapper.update(user_update);
            return BaseResponse.builder().data(user_update.getId()).build();
        } else{
            return BaseResponse.builder().code(ErrorCode.VERIFY_CODE_FAILED).build();

        }
    }

    /**
     * 用户登录
     * @param userParam
     * @return
     */
    public BaseResponse doLogin(UserLoginParam userParam, String appSource) {
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
            return login(users.get(0), appSource);
        } else if(users.size() < 1){
            return BaseResponse.builder().code(ErrorCode.LOGIN_FAIL).build();
        } else {
            throw new InternalException("手机及验证码重复");
        }
    }
    public BaseResponse innerLogin(String userId, String appSource){
        User user = mapper.findOne(userId);
        return login(user, appSource);
    }
    public BaseResponse login(User user, String appSource){
        Date now = new Date();
//        User user_db = users.get(0);
        user.setLastLoginTime(now);
        User user_update = User.builder().lastLoginTime(now).build();
        user_update.setId(user.getId());
        mapper.update(user_update);
        Map<String, Object> result = new HashMap<>();
        String authorization = "Basic" + UuidUtil.generate().toUpperCase();
        result.put("id", user.getId());
        result.put("avatar", user.getAvatar());
        // 返回用户基本信息
        // 从enterprise_user表中查询所有的企业
        // 如果是管理员则不返回
        log.info("userparam appsource: " + appSource);
        if(user.getUserType().equals(User.COMMON) && CommonConstant.APP_SOURCE_ADMIN.equals(appSource)) {
            // 普通用户登录后台要拒绝
            log.info("当前用户%s,　为普通用户无权限登录后台管理");
            return BaseResponse.builder().code(ErrorCode.ADMIN_LOGIN_DENIED).build();
        }
        List<EnterpriseUser> enterprises = enterpriseUserMapper.findAll(EnterpriseUserQuery.builder().userId(user.getId()).build());
        user.setEnterprises(enterprises);
//            if(user_db.getUserType().equals(User.COMMON)){
//                List<Block> roleList = roleMapper.findGrantedBlock(user_db.getId());
//                result.put("roles", roleList);
//            }
        result.put("enterprises", enterprises);
        result.put("expireTime", new Date(now.getTime() + LoginUtil.COOKIE_MAX_INACTIVE_INTERVAL));
        result.put("userType", user.getUserType());
        LoginUtil.getInstance().setUser(authorization, user);
        result.put("authorization", user.getAuth());
        return BaseResponse.builder().data(result).build();
    }

    public BaseResponse doWxLogin(UserWXLoginParam param){
        Map<String, String> wechatInfo = WechatWeProgramUtil.getUserInfo(param.getWeprogramId(), param.getCode(),param.getEncryptedData(), param.getIv());
        if(wechatInfo == null) {
            return BaseResponse.builder().code(ErrorCode.WECHAT_LOGIN_VERIFY_FAIL).build();
        }
        User user = mapper.findOneByMobile(wechatInfo.get("mobile"));
        if(user == null){
            add(User.builder().mobile(wechatInfo.get("mobile")).userType(User.COMMON).openid(wechatInfo.get("openid")).unionid(wechatInfo.get("unionid")).build());
        } else if (StringUtils.isEmpty(user.getOpenid()) || StringUtils.isEmpty(user.getUnionid())) {
            mapper.update(User.builder().id(user.getId()).openid(wechatInfo.get("openid")).unionid(wechatInfo.get("unionid")).build());
        }
        if(user == null || StringUtils.isEmpty(user.getPassword())){
            return BaseResponse.builder().code(ErrorCode.WECHAT_NO_PASSWD).data(wechatInfo).build();
        } else {
            return doLogin(UserLoginParam.builder().mobile(user.getMobile()).password(user.getPassword()).build(), CommonConstant.APP_SOURCE_WEIXIN);
        }
    }

    public BaseResponse wxUpdatePassword(UserWXUpdatePasswordParam param){
        User user = mapper.findOneByMobile(param.getMobile());
        if(user != null && param.getOpenid().equals(user.getOpenid())){
            mapper.update(User.builder().id(user.getId()).password(param.getPassword()).build());
            return doLogin(UserLoginParam.builder().mobile(param.getMobile()).password(param.getPassword()).build(), CommonConstant.APP_SOURCE_WEIXIN);
        } else {
            return BaseResponse.builder().code(ErrorCode.WECHAT_LOGIN_VERIFY_FAIL).build();
        }
    }


    public BaseResponse updateProfile(UserProfileParam param){
        mapper.updateProfile(param);
        return BaseResponse.builder().build();
    }

}

