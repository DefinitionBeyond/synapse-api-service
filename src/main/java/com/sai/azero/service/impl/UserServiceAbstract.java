package com.sai.azero.service.impl;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.sai.azero.dao.UserDao;
import com.sai.azero.po.RegisterPo;
import com.sai.azero.po.UserPo;
import com.sai.azero.po.UserResponse;
import com.sai.azero.util.ResponseUtil;
import com.sai.azero.util.TokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sai.azero.util.CodeConstant.CONNECTION_MATRIX_FAILURE;
import static com.sai.azero.util.CodeConstant.USER_EXSEiT;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/23 10:42
 */

@Log4j2
@Component
@EnableApolloConfig
public abstract class UserServiceAbstract {
    @Value("${synapse.default.token.location}")
    protected String location;

    @Value("${synapse.default.token.identifier}")
    protected String identifier;

    @Value("${synapse.default.token.secretKey}")
    protected String secretKey;

    @Value("${synapse.default.token.validTime}")
    protected long validTime;

    @Value("${synapse.default.matrix.register.password}")
    protected String password;

    @Value("${synapse.default.matrix.register.type}")
    protected String type;

    @Value("${synapse.default.matrix.register.url}")
    protected String registerUrl;

    protected String getUserIdByUsername(String userName) {
        return "@" + userName + ":" + this.location;
    }

    @Autowired
    UserDao dao;

    protected Mono<ResponseEntity<?>> registerToMatrix(UserPo po) {

        return this.register(po.getUserName(), po.getDeviceId()).flatMap(res -> {
            if (!CollectionUtils.isEmpty(res)) {
                UserResponse response = UserResponse.builder()
                        .userId(po.getUserId())
                        .loginToken(po.getLoginToken())
                        .deviceId(po.getDeviceId())
                        .azeroUserId(po.getAzeroUserId())
                        .createTime(po.getCreateTime())
                        .build();
                dao.saveUser(po);
                return ResponseUtil.generalResponse(HttpStatus.OK, response);
            }
            return ResponseUtil.generalResponse(HttpStatus.INTERNAL_SERVER_ERROR, CONNECTION_MATRIX_FAILURE.getMsg());
        }).onErrorResume(e -> {
            log.error("Register failure by username {}", po.getUserName(), e);
            return ResponseUtil.generalResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        });

    }

    protected String getToken(UserPo po) {
        String token = new String();
        if (validTime > 0) {
            token = new TokenUtil(po.getUserName(), location, secretKey, po.getDeviceId(), identifier, validTime).getToken();
        } else {
            token = new TokenUtil(po.getUserName(), location, secretKey, po.getDeviceId(), identifier).getToken();
        }
        return token;
    }

    private Mono<Map> register(String username, String deviceId) {
        RegisterPo request = RegisterPo.builder().password(password).username(username).device_id(deviceId).auth(RegisterPo.Auth.builder().type(type).build()).build();
        return WebClient.create(registerUrl).post()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .syncBody(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.BAD_REQUEST),
                        clientResponse -> {
                            throw Exceptions.propagate(new Exception(USER_EXSEiT.getMsg()));
                        })
                .bodyToMono(Map.class);
    }

    protected boolean checkRequest(UserPo request) {
        return StringUtils.isEmpty(request.getDeviceId()) || StringUtils.isEmpty(request.getUserName()) ? false : true;
    }

    protected boolean checkConflictUser(List<UserPo> userPoList, UserPo request) {
        if (CollectionUtils.isEmpty(userPoList)) {
            return false;
        }
        List<UserPo> collect = userPoList.stream()
                .filter(e -> e.getAzeroUserId().equals(request.getAzeroUserId()) &&
                        e.getUserId().equals(request.getUserId()) &&
                        e.getDeviceId().equals(request.getDeviceId()))
                .collect(Collectors.toList());

        return !CollectionUtils.isEmpty(collect);

    }

}
