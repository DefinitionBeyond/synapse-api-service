package com.sai.azero.dao;

import com.sai.azero.mapper.UserMapper;
import com.sai.azero.po.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 18:43
 */
@Component
public class LoginTokenDao {
    @Autowired
    UserMapper mapper;

    public UserPo selectUserByUserId(String userId){
        return mapper.findOne(userId);
    }

    public int saveUser(UserPo user){
        return mapper.insert(user);
    }

    public int deleteByDeviceIdAndLoginToken(UserPo user){
        return mapper.deleteByDeviceIdAndLoginToken(user);
    }

}
