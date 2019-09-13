package cn.duan.community.controller;

import cn.duan.community.common.cache.TagCache;
import cn.duan.community.model.Topic;
import cn.duan.community.service.TopicService;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RouteController {

    @Value("${github.client.id}")
    private String id ;

    @Value("${github.client.callback}")
    private String callback ;

    @Autowired
    private TopicService topicService;


    @GetMapping("/publish")
    public ModelAndView publish(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        //将公用的数据放入session中
        List<Topic> topicList = topicService.list();
        request.getSession().setAttribute("tags",TagCache.get());
        request.getSession().setAttribute("topicList",topicList);
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
    @GetMapping("/1")
    public String test(){
        return "1";
    }
}
