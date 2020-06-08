package com.sai.azero.service.impl;

import static com.sai.azero.util.CodeConstant.AZERO_ID_USED;
import static com.sai.azero.util.CodeConstant.MISS_PARAMETER;
import static com.sai.azero.util.CodeConstant.OK;
import static com.sai.azero.util.CodeConstant.SAVE_DATABEASE_FAILURE;
import static com.sai.azero.util.CodeConstant.SERVER_ERROT;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sai.azero.po.UserPo;
import com.sai.azero.po.UserResponse;
import com.sai.azero.service.UserService;
import com.sai.azero.util.ResponseUtil;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 18:00
 */
@Log4j2
@Service
public class UserServiceImpl extends UserServiceAbstract implements UserService {

    @Transactional
    @Override
    public Mono<ResponseEntity<?>> loginToken(UserPo request) {
        log.info("Login token api request {}", request);
        boolean flag = this.checkRequest(request);
        if (!flag || StringUtils.isEmpty(request.getAzeroUserId())) {
            return ResponseUtil.generalResponse(HttpStatus.BAD_REQUEST, MISS_PARAMETER.getMsg());
        }

        String userId = getUserIdByUsername(request.getUserName());
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        request.setUserId(userId);
        request.setCreateTime(createTime);
        String loginToken = getToken(request);
        request.setLoginToken(loginToken);

        // 检查azero是否已经被不同的userId使用
        List<UserPo> userPoList = dao.findAllByAzeroId(request.getAzeroUserId());
        if (dao.userExists(userId)) {

            UserResponse response = UserResponse.builder()
                    .azeroUserId(request.getAzeroUserId())
                    .deviceId(request.getDeviceId())
                    .loginToken(loginToken)
                    .userId(userId)
                    .createTime(createTime)
                    .build();
            try {
            	UserPo userPo = checkConflictUser(userPoList,request);
                if (null == userPo) {
                    dao.saveUser(request);
                }else {
                	response.setLoginToken(userPo.getLoginToken());
                }
                return ResponseUtil.generalResponse(HttpStatus.OK, response);
            } catch (Exception e) {
                log.error("Save userinfo failure, cur userinfo {}", request, e);
                return ResponseUtil.generalResponse(HttpStatus.INTERNAL_SERVER_ERROR, SAVE_DATABEASE_FAILURE.getMsg());
            }
        } else {
            if (CollectionUtils.isEmpty(userPoList)) {
                dao.saveUser(request);
                return registerToMatrix(request);
            }else {
                return ResponseUtil.generalResponse(HttpStatus.BAD_REQUEST, AZERO_ID_USED.getMsg());
            }
        }

    }

    @Override
    public ResponseEntity<String> logout(UserPo request) {
        if (log.isDebugEnabled()) {
            log.debug("Logout token api request {}", request);
        }
        boolean flag = this.checkRequest(request);
        if (!flag || StringUtils.isEmpty(request.getLoginToken())) {
            return new ResponseEntity<String>(MISS_PARAMETER.getMsg(), HttpStatus.BAD_REQUEST);
        }
        try {
            dao.deleteByDeviceIdAndLoginToken(request);
            if (log.isDebugEnabled()) {
                log.debug("Delete user info success by request {}", request);
            }
            return new ResponseEntity<String>(OK.getMsg(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Delete user info failure by request {}", request, e);
            return new ResponseEntity<String>(SERVER_ERROT.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
