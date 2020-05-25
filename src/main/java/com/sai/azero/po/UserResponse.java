package com.sai.azero.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/25 16:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String deviceId;
    private String loginToken;
    private String azeroUserId;
    private String userId;
    private Timestamp createTime;
}
