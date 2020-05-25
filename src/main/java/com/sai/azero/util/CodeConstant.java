package com.sai.azero.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 16:27
 */
@Getter
@AllArgsConstructor
public enum CodeConstant {
    OK("success"),
    MISS_PARAMETER("Missing parameter"),
    CONNECTION_MATRIX_FAILURE("Connection matrix error"),
    SAVE_DATABEASE_FAILURE("Save userinfo failure"),
    REGISTER_FAILURE("Reg to matrix failure"),
    USER_EXSEiT("User already reg"),
    SERVER_ERROT("Internal server error");
//    private int code;
    private String msg;
}
