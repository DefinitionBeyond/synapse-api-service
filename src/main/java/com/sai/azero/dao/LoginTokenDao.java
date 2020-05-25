package com.sai.azero.dao;

import com.sai.azero.mapper.UserMapper;
import com.sai.azero.po.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 18:43
 */
@Component
public class LoginTokenDao {
    @Autowired
    UserMapper mapper;

    public List<UserPo> selectUserByUserId(String userId){
        return mapper.findAllByUserId(userId);
    }

//    public List<UserPo> selectAccessTokenByAzeroUserId(String azeroUserId){
//        return mapper.selectAccessTokenByAzeroUserId(azeroUserId);
//    }

    public boolean userExists(String userId){
        return mapper.userExists(userId);
    }

    public int saveUser(UserPo user){
        return mapper.insert(user);
    }

    public int deleteByDeviceIdAndLoginToken(UserPo user){
        return mapper.deleteByDeviceIdAndLoginToken(user);
    }

}
