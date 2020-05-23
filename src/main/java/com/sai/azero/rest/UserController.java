package com.sai.azero.rest;

import com.sai.azero.po.UserPo;
import com.sai.azero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author liutao
 * @CreateTime 2020/5/22 15:55
 */
@Controller
@RequestMapping("/_matrix/client/r0/login_token")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Mono<ResponseEntity<?>> loginToken(@RequestBody UserPo request){
        return userService.loginToken(request);
    }
    @RequestMapping(value = "/destruct",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> logoutToken(@RequestBody UserPo request){
        return userService.logout(request);
    }

}
