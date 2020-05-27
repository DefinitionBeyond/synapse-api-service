package com.sai.azero.dao;

import com.sai.azero.mapper.RoomMapper;
import com.sai.azero.po.RoomInfo;
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

    public List<RoomInfo> getRoomList(String userId){
        return roomMapper.findGroupRoomByUserId(userId);
    }

    public List<RoomInfo> getDirectRoomList(String userId){
        return roomMapper.findDirectRoomByUserId(userId);
    }
}
