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
}
