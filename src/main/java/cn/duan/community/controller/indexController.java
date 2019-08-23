package cn.duan.community.controller;

import cn.duan.community.dto.PaginationDTO;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value = "size",defaultValue = "2") Integer size){
        PaginationDTO<QuestionDTO> pagination = questionService.list(page, size);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pagination",pagination);
        mv.setViewName("/index");
        return mv;
    }
}
