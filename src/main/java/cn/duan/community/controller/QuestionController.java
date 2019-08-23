package cn.duan.community.controller;

import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;




    @PostMapping("/question")
    public String addQuestion(@RequestParam(value = "title",required = false) String title,
                              @RequestParam(value = "description",required = false) String description,
                              @RequestParam(value = "tag",required = false) String tag,
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
        User user = userMapper.finbByToken(token);
        question.setCreator(Integer.parseInt(user.getId()));
        questionMapper.add(question);
        return "publish";
    }
}
