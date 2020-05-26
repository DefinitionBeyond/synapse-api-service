package com.sai.azero.dao;

import com.sai.azero.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/26 11:53
 */
@Component
public class RoomDao {
    @Autowired
    RoomMapper roomMapper;

    public List<Object[]> getRoomList(String userId){
        return roomMapper.findGroupRoomByUserId(userId);
    }

    public List<Object[]> getDirectRoomList(String userId){
        return roomMapper.findDirectRoomByUserId(userId);
    }
}
