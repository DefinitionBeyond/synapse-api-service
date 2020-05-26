package com.sai.azero.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/26 11:57
 */
public interface RoomService {
    Mono<ResponseEntity<?>> getRoomList(String azeroUserId);
}
