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
public class UserDao {
    @Autowired
    UserMapper mapper;

    public List<UserPo> selectUserByUserId(String userId){
        return mapper.findAllByUserId(userId);
    }

    public String findUserIdByAzeroId(String azeroUserId){
        return mapper.findUserIdByAzeroId(azeroUserId);
    }

    public List<UserPo> findAllByAzeroId(String azeroUserId){
        return mapper.findAllByAzeroId(azeroUserId);
    }

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
