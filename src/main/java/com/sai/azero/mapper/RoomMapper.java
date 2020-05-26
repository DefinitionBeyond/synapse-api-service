package com.sai.azero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/26 11:47
 */
@Mapper
public interface RoomMapper {

    @Select("\n" +
            "SELECT DISTINCT m.user_id, m.room_id, rss.name\n" +
            "    , rsh.local_users_in_room,ud.display_name\n" +
            "FROM current_state_events c\n" +
            "    INNER JOIN events e USING (room_id, event_id)\n" +
            "    INNER JOIN room_memberships m USING (room_id)\n" +
            "    INNER JOIN room_stats_state rss USING (room_id)\n" +
            "    INNER JOIN room_stats_historical rsh USING (room_id)\n" +
            "    INNER JOIN user_directory ud ON (m.user_id = ud.user_id)\n" +
            "WHERE c.type = 'm.room.member'\n" +
            "    AND c.state_key = '{user_id}'\n" +
            "    AND m.user_id != '{user_id}'\n" +
            "    AND m.membership = 'join'\n" +
            "    AND rss.name IS NULL")
    List<Object[]> findDirectRoomByUserId(@Param("userId") String userId);

    @Select("SELECT user_id,room_id,  rss.name, rsh.local_users_in_room\n" +
            "FROM current_state_events AS c\n" +
            "    INNER JOIN room_memberships AS m USING (room_id, event_id)\n" +
            "    INNER JOIN events AS e USING (room_id, event_id)\n" +
            "    INNER JOIN room_stats_state AS rss USING (room_id)\n" +
            "    INNER JOIN room_stats_historical AS rsh USING (room_id)\n" +
            "WHERE\n" +
            "    c.type = 'm.room.member'\n" +
            "    AND state_key = #{userId} \n" +
            "    AND m.membership = 'join'\n" +
            "    AND rss.name IS NOT NULL")
    List<Object[]> findGroupRoomByUserId(@Param("userId")String userId);
}
