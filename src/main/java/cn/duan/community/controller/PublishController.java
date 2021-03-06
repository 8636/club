package cn.duan.community.controller;

import cn.duan.community.common.cache.TagCache;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import cn.duan.community.service.TopicService;
import cn.duan.community.service.impl.QuestionDTOServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionDTOServiceImpl questionService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TopicService topicService;


    /**
     * @param id
     * @return
     */
    @GetMapping("/publish/{id}")
    public ModelAndView findById(@PathVariable("id") Long id,
                                 HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        QuestionDTO questionDTO = questionService.getById(id);
        User user = (User)request.getSession().getAttribute("user");
        if (user == null || !questionDTO.getCreator().equals(user.getId())){
            mv.setView(new RedirectView("/question/"+id));
            return mv;
        }
        mv.addObject("title", questionDTO.getTitle());
        mv.addObject("description", questionDTO.getDescription());
        mv.addObject("questionTag", questionDTO.getTag());
        mv.addObject("tags",TagCache.get());
        mv.addObject("question", questionDTO);
        List<Topic> topicList = topicService.list();
        mv.addObject("topicList", topicList);

        mv.setViewName("publish");
        return mv;
    }

    @PostMapping("/publish")
    public String addQuestion(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "tag", required = false) String tag,
                              @RequestParam(value = "topic", required = false) Long topic,
                              @RequestParam(value = "id", required = false) Long id,
                              @CookieValue("token") String token, Model model) {
        //将输入的信息回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("topic", topic);

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setTopicId(topic);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("token", token);
        List<User> userList = userMapper.selectByExample(example);
        question.setCreator(userList.get(0).getId());
        question.setId(id);
        questionService.craeteOrUpdate(question);
        return "redirect:";
    }
}
