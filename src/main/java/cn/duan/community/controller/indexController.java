package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.User;
import cn.duan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    User user = userMapper.finbByToken(cookie.getValue());
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                }
            }
        }
        List<QuestionDTO> questionDTOList =  questionService.list();
        ModelAndView mv = new ModelAndView();
        mv.addObject("questionList",questionDTOList);
        mv.setViewName("/index");
        return mv;
    }
}
