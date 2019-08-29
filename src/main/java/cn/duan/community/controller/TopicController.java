package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import cn.duan.community.service.TopicService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topic")
    public ModelAndView topic(){
        ModelAndView mv = new ModelAndView();

        List<Topic> topicList = topicService.list();
        mv.addObject("topics",topicList);
        mv.setViewName("topic");
        return mv;
    }


    @GetMapping("/topic/{name}")
    public ModelAndView topicInfo(@PathVariable("name") String topicName,
                                  @RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "size",defaultValue = "10") Integer size){
        ModelAndView mv = new ModelAndView();
        Topic topic = topicService.findByName(topicName);
        PageInfo<QuestionDTO> pagreInfo = topicService.findQuestionListByName(topicName, page, size);
        List<User> userList = topicService.findUserListByTopicId(topic.getId());
        mv.addObject("userList",userList);
        mv.addObject("pagreInfo",pagreInfo);
        mv.addObject("topic",topic);
        mv.setViewName("topicInfo");
        return mv;
    }
}
