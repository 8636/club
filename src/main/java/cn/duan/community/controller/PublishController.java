package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import cn.duan.community.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionDTOService questionService;


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/publish/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id){
        QuestionDTO questionDTO = questionService.getById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("title",questionDTO.getTitle());
        mv.addObject("description",questionDTO.getDescription());
        mv.addObject("tag",questionDTO.getTag());
        mv.addObject("question",questionDTO);
        mv.setViewName("publish");
        return mv;
    }
    @PostMapping("/publish")
    public String addQuestion(@RequestParam(value = "title",required = false) String title,
                              @RequestParam(value = "description",required = false) String description,
                              @RequestParam(value = "tag",required = false) String tag,
                              @RequestParam(value = "id",required = false) Integer id,
                              @CookieValue("token") String token, Model model){
        //将输入的信息回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description==""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("token",token);
        List<User> userList = userMapper.selectByExample(example);
        question.setCreator(userList.get(0).getId());
        question.setId(id);
        questionService.craeteOrUpdate(question);
        return "publish";
    }
}
