package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import cn.duan.community.service.QuestionDTOService;
import cn.duan.community.service.TopicService;
import cn.duan.community.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionDTOService questionDTOService;
    @Autowired
    private TopicService topicService;

    @GetMapping("/people/{name}")
    public ModelAndView people(@PathVariable("name") String username,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size){

        User user = userService.findUserByName(username);
        PageInfo<QuestionDTO> pageInfo = questionDTOService.findQuestionByUserId(user.getId(),page,size);
        ModelAndView modelAndView = new ModelAndView();
        //关注的话题
        List<Topic> topicList = topicService.findTopicByUserName(username);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("topicList",topicList);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("people");
        return modelAndView;

    }
}
