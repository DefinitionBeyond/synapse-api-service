package com.sai.azero.service.impl;

import com.sai.azero.po.UserPo;
import com.sai.azero.service.UserService;
import com.sai.azero.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.sai.azero.util.CodeConstant.MISS_PARAMETER;
import static com.sai.azero.util.CodeConstant.OK;
import static com.sai.azero.util.CodeConstant.SERVER_ERROT;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 18:00
 */
@Log4j2
@Service
public class UserServiceImpl extends UserServiceAbstract implements UserService {

    @Override
    public Mono<ResponseEntity<?>> loginToken(UserPo request) {
        log.info("Login token api request {}",request);
        boolean flag = this.checkRequest(request);
        if (!flag) {
            return ResponseUtil.generalResponse(HttpStatus.BAD_REQUEST, MISS_PARAMETER.getMsg());
        }
        String userId = getUserIdByUsername(request.getUserName());
        UserPo userPo = dao.selectUserByUserId(userId);
        if (Objects.isNull(userPo)) {
            request.setUserId(userId);

            return registerToMatrix(request);
        }
        return ResponseUtil.generalResponse(HttpStatus.OK, userPo);
    }

    @Override
    public ResponseEntity<String> logout(UserPo request) {
        log.info("Logout token api request {}",request);
        boolean flag = this.checkRequest(request);
        if (!flag || StringUtils.isEmpty(request.getLoginToken())) {
            return new ResponseEntity<String>(MISS_PARAMETER.getMsg(),HttpStatus.BAD_REQUEST);
        }
        try {
            dao.deleteByDeviceIdAndLoginToken(request);
            log.info("Delete user info success by request {}",request);
            return new ResponseEntity<String>(OK.getMsg(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Delete user info failure by request {}",request,e);
            return new ResponseEntity<String>(SERVER_ERROT.getMsg(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkRequest(UserPo request) {
        return StringUtils.isEmpty(request.getDeviceId()) || StringUtils.isEmpty(request.getUserName()) ? false : true;
    }
}