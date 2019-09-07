package cn.duan.community.controller;


import cn.duan.community.common.enums.ExceptionEnum;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class LoginController {


    @PostMapping("/user/login")
    public ResultDTO login(String username, String password, String code, Boolean remember){
        log.info(username,password);
        throw  new CustomException(ExceptionEnum.LOGIN_NOT_ALLOW);
    }

}
