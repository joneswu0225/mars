package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.exception.InternalException;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.User;
import com.jones.mars.model.constant.UserType;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.query.EnterpriseQuery;
import com.jones.mars.model.param.UserLoginParam;
import com.jones.mars.model.param.UserRegistParam;
import com.jones.mars.model.query.UserQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.EnterpriseMapper;
import com.jones.mars.repository.UserMapper;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.Md5Util;
import com.jones.mars.util.RandomString;
import com.jones.mars.util.UuidUtil;
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

    private boolean exists(String mobile){
        User user = new User();
        user.setMobile(mobile);
        Integer count = mapper.findCount(new Query(user));
        return count > 0;
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
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse regist(UserRegistParam param){
        if(!exists(param.getMobile())){
            User user = new User();
            user.setMobile(param.getMobile());
            // TODO 对密码加密
            user.setPassword(param.getPassword());
            user.setUserType(UserType.COMMON);
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
        List<User> users = mapper.findList(UserQuery.builder().mobile(mobile).build());
        if(users.size() == 1){
            User user_db = users.get(0);
            User user_update = new User();
            user_update.setId(user_db.getId());
            user_update.setVerifyCode(verifyCode);
            mapper.update(user_update);
            Map<String, String> resp = new HashMap<>();
            resp.put("verifyCode", verifyCode);
            return BaseResponse.builder().data(resp).build();
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
    public BaseResponse resetPassword(User param){
        List<User> users = mapper.findList(new Query(param));
        if(users.size() == 1){
            User user_db = users.get(0);
            User user_update = new User();
            user_update.setId(user_db.getId());
            user_update.setPassword(param.getPassword());
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
     * TODO 登录信息放到session
     * @param userParam
     * @return
     */
    public BaseResponse doLogin(UserLoginParam userParam) {
        UserQuery.UserQueryBuilder builder = UserQuery.builder();
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
            User user_update = new User();
            user_update.setId(user_db.getId());
            user_update.setLastLoginTime(now);
            mapper.update(user_update);
            Map<String, Object> result = new HashMap<>();
            result.put(LoginUtil.CUR_USER, "Basic " + UuidUtil.generate().toUpperCase());
            result.put("id", user_db.getId());
            result.put("avatar", user_db.getAvatar());
            // 返回用户基本信息
            // 从enterprise_user表中查询所有的企业
            // 如果是管理员则不返回
            if(!user_db.getUserType().equals(User.ADMIN)){
                List<Enterprise> enterprises = enterpriseMapper.findUserEnterprise(EnterpriseQuery.builder().userId(user_db.getId()).build());
                result.put("enterprises", enterprises);
                user_db.setEnterprises(enterprises);
            }
            result.put("expireTime", new Date(now.getTime() + LoginUtil.SESSION_MAX_INACTIVE_INTERVAL));
            LoginUtil.getInstance().setUser(user_db);
            return BaseResponse.builder().data(result).build();
        } else if(users.size() < 1){
            return BaseResponse.builder().code(ErrorCode.LOGIN_FAIL).build();
        } else {
            throw new InternalException("手机及验证码重复");
        }
    }

    @Override
    public BaseMapper getMapper() {
        return null;
    }
}

