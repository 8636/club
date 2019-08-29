package cn.duan.community.controller;

import cn.duan.community.common.cache.TagCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RounteController {

    @Value("${github.client.id}")
    private String id ;

    @Value("${github.client.callback}")
    private String callback ;

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

    @GetMapping("/githubLogin")
    public String redirectGithub(){
        return "redirect:https://github.com/login/oauth/authorize?client_id="+id+"&redirect_uri="+callback+"&scope=user&state=1";
    }

    @GetMapping("/people")
    public String people(){
        return "people";
    }
}
