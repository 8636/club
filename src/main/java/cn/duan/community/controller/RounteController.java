package cn.duan.community.controller;

import cn.duan.community.common.cache.TagCache;
import cn.duan.community.model.Topic;
import cn.duan.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RounteController {

    @Value("${github.client.id}")
    private String id ;

    @Value("${github.client.callback}")
    private String callback ;

    @Autowired
    private TopicService topicService;


    @GetMapping("/publish")
    public ModelAndView publish() {
        ModelAndView mv = new ModelAndView();
        List<Topic> topicList = topicService.list();
        mv.addObject("tags", TagCache.get());
<<<<<<< HEAD
        mv.addObject("topicList", topicList);
=======
>>>>>>> b7e2c1752da785be88890552e3093201ddd5b21e
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
