package cn.duan.community.controller;


import cn.duan.community.dto.ResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {


    @PostMapping("/user/login")
    public ResultDTO login(String username, String password, String code, Boolean remember){

        System.out.println(username);
        System.out.println(password);
        return null;
    }

}
