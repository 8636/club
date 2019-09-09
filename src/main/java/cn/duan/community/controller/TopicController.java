package cn.duan.community.controller;

import cn.duan.community.common.enums.ExceptionEnum;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.dto.ResultDTO;
import cn.duan.community.mapper.TopicMapper;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import cn.duan.community.service.TopicService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicMapper topicMapper;

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

        //新增话题   统计 近三天创建的话题
        List<Topic> newTopics = topicService.newTopics();
        mv.addObject("userList",userList);
        mv.addObject("pagreInfo",pagreInfo);
        mv.addObject("topic",topic);
        mv.addObject("newTopics",newTopics);
        mv.setViewName("topicInfo");
        return mv;
    }

    @GetMapping("/focusTopic/{id}")
    @ResponseBody
    public ResultDTO focusTopic(HttpServletRequest request,
                                @PathVariable("id") Long topicId){
        User user = (User)request.getSession().getAttribute("user");
        //避免重复关注
        List<Long> topicIds = topicMapper.listUserTopics(user.getId());
        if (topicIds != null && topicIds.size() > 0){
            if (topicIds.contains(topicId)){
                return ResultDTO.errorOf(ExceptionEnum.FOCUS_TOPIC_AGAIN);
            }
        }
        int i = topicService.focusTopic(user.getId(), topicId);
        if (i==0){
            return ResultDTO.errorOf(ExceptionEnum.FOCUS_TOPIC_FAIL);
        }
        return ResultDTO.okOf();
    }
}
