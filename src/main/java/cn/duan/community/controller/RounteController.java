package cn.duan.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RounteController {

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/question")
    public String question(){
        return "question";
    }

}
