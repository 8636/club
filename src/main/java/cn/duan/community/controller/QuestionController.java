package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class QuestionController {

    @Autowired
    private QuestionDTOService questionService;

    @GetMapping("/question/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id){
        QuestionDTO questionDTO = questionService.getById(id);
        ModelAndView mv = new ModelAndView();
        questionService.insViewCount(id);
        mv.addObject("question",questionDTO);
        mv.setViewName("question");
        return mv;
    }
}
