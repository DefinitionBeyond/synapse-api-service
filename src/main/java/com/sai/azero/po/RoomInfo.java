package com.sai.azero.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/27 10:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfo{
    private String userId;
    private String roomId;
    private String name;
    private Integer localUsersInRoom;
    private String displayName;
}

