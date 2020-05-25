package com.sai.azero.service.impl;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.sai.azero.dao.LoginTokenDao;
import com.sai.azero.po.RegisterPo;
import com.sai.azero.po.UserPo;
import com.sai.azero.util.ResponseUtil;
import com.sai.azero.util.TokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

import static com.sai.azero.util.CodeConstant.CONNECTION_MATRIX_FAILURE;
import static com.sai.azero.util.CodeConstant.SAVE_DATABEASE_FAILURE;

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
    LoginTokenDao dao;

    @Transactional
    protected Mono<ResponseEntity<?>> registerToMatrix(UserPo po) {

        po.setCreateTime(new Timestamp(System.currentTimeMillis()));
        String token = new String();
        if (validTime > 0) {
            token = new TokenUtil(po.getUserName(), location, secretKey, po.getDeviceId(), identifier, validTime).getToken();
        } else {
            token = new TokenUtil(po.getUserName(), location, secretKey, po.getDeviceId(), identifier).getToken();
        }
        po.setLoginToken(token);

        log.info("Cur user isn't exists , user info {}", po);

        try {
            dao.saveUser(po);
        }catch (Exception e){
            log.error("Save userinfo failure, cur userinfo {}", po, e);
            return ResponseUtil.generalResponse(HttpStatus.INTERNAL_SERVER_ERROR, SAVE_DATABEASE_FAILURE);
        }
        return this.register(po.getUserName()).flatMap(flag -> {
            if (flag) {
                return ResponseUtil.generalResponse(HttpStatus.OK, po);
            } else {
                return ResponseUtil.generalResponse(HttpStatus.INTERNAL_SERVER_ERROR, CONNECTION_MATRIX_FAILURE);
            }
        });
    }

    private Mono<Boolean> register(String username) {
        RegisterPo request = RegisterPo.builder().password(password).username(username).auth(RegisterPo.Auth.builder().type(type).build()).build();
        return WebClient.create(registerUrl).post()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .syncBody(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).flatMap(res -> {
                    log.info("Register success by username {}", username);
                    return Mono.just(true);
                }).onErrorResume(e -> {
                    log.error("Register failure by username {}", username, e);
                    return Mono.just(false);
                });
    }

}
