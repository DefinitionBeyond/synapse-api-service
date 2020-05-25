package com.sai.azero.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/23 10:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPo {
    private String userName;
    private String deviceId;
    private String loginToken;
    private String userId;
    private Timestamp createTime;
}
