package cn.duan.community.controller;

import cn.duan.community.common.cache.TagCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RounteController {

    @GetMapping("/publish")
    public ModelAndView publish() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("tags", TagCache.get());
        mv.setViewName("publish");
        return mv;
    }

    @GetMapping("/question")
    public String question() {
        return "question";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
