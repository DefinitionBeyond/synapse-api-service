package com.sai.azero.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 16:24
 */
public class ResponseUtil {
    public static <T> Mono<ResponseEntity<?>> generalResponse(HttpStatus status, T response) {
        return Mono.just(new ResponseEntity<T>(response, status));
    }

}
