package com.sai.azero.service.impl;

import com.sai.azero.dao.RoomDao;
import com.sai.azero.dao.UserDao;
import com.sai.azero.po.RoomsResponsePo;
import com.sai.azero.service.RoomService;
import com.sai.azero.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.sai.azero.util.CodeConstant.MISS_PARAMETER;
import static com.sai.azero.util.CodeConstant.SERVER_ERROT;
import static com.sai.azero.util.CodeConstant.USER_NOT_EXSEiT;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/26 11:58
 */
@Log4j2
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomDao roomDao;

    @Autowired
    UserDao userDao;


    public Mono<ResponseEntity<?>> getRoomList(String azeroUserId) {
        if (StringUtils.isEmpty(azeroUserId)) {
            log.error("AzeroUserId parameter not found: {}", azeroUserId);
            return ResponseUtil.generalResponse(HttpStatus.BAD_REQUEST, MISS_PARAMETER.getMsg());
        }
        try {
            String userId = userDao.findUserIdByAzeroId(azeroUserId);
            if (StringUtils.isEmpty(userId)) {
                log.error("This azeroUserId not reg: {}", azeroUserId);
                return ResponseUtil.generalResponse(HttpStatus.BAD_REQUEST, USER_NOT_EXSEiT.getMsg());
            }
            List<Object[]> directRoomList = roomDao.getDirectRoomList(userId);
            List<Object[]> roomList = roomDao.getRoomList(userId);
            RoomsResponsePo response = convertResponse(directRoomList, roomList);
            return ResponseUtil.generalResponse(HttpStatus.OK, response);
        } catch (Exception e) {
            log.error("User info query failure azeroUserId {}", azeroUserId, e);
            return ResponseUtil.generalResponse(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROT.getMsg());
        }

    }

    private RoomsResponsePo convertResponse(List<Object[]> directRoomList, List<Object[]> roomList) {
        RoomsResponsePo.RoomsResponsePoBuilder builder = RoomsResponsePo.builder();

        if (!CollectionUtils.isEmpty(directRoomList)) {
            builder.directRooms(convertArrayToRoomInfoList(directRoomList));
        }
        if (!CollectionUtils.isEmpty(roomList)) {
            builder.directRooms(convertArrayToRoomInfoList(roomList));
        }
        return builder.build();
    }

    private List<RoomsResponsePo.RoomInfo> convertArrayToRoomInfoList(List<Object[]> arrays) {
        return arrays.stream().map(r -> {
            RoomsResponsePo.RoomInfo roomInfo = RoomsResponsePo.RoomInfo.builder()
                    .userId(String.valueOf(r[0]))
                    .roomId(String.valueOf(r[1]))
                    .name(String.valueOf(r[2]))
                    .localUsersInRoom(Integer.parseInt(String.valueOf(r[3])))
                    .build();
            if (r.length == 5) {
                roomInfo.setDisplayName(String.valueOf(r[4]));
            }
            return roomInfo;
        }).collect(Collectors.toList());
    }


}
