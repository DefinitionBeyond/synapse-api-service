package com.sai.azero.service;

import com.sai.azero.po.UserPo;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 17:57
 */
public interface UserService {
    Mono<ResponseEntity<?>> loginToken(UserPo request);
    ResponseEntity<String> logout(UserPo request);
}
