package com.sai.azero.mapper;

import com.sai.azero.po.UserPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/23 10:11
 */
@Mapper
public interface UserMapper{
    @Select("select * from login_tokens where 1=1 and user_id = #{userId}")
    UserPo findOne(@Param("userId") String userId);

    @Insert("insert into login_tokens(user_id,device_id,login_token,create_time) values(#{userId},#{deviceId},#{loginToken},#{createTime}) on duplicate key update login_token = #{loginToken}")
    int insert(UserPo user);

    @Delete("delete from login_tokens where 1=1 and login_token = #{loginToken} and device_id = ${deviceId}")
    int deleteByDeviceIdAndLoginToken(UserPo user);

}
