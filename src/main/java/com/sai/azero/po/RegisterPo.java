package com.sai.azero.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/23 11:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPo {
    private String password;
    private String username;
    private Auth auth;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth{
        private String type;
    }

}
