package com.sai.azero.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/26 16:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomsResponsePo {
    List<RoomInfo> rooms;
    List<RoomInfo> directRooms;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfo{
        private String userId;
        private String roomId;
        private String name;
        private Integer localUsersInRoom;
        private String displayName;
    }
}
