package cn.duan.community.controller;

import cn.duan.community.dto.CommentDTO;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.service.CommentService;
import cn.duan.community.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionDTOService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public ModelAndView findById(@PathVariable("id") Long id){
        QuestionDTO questionDTO = questionService.getById(id);
        ModelAndView mv = new ModelAndView();
        //问题下面的评论
        List<CommentDTO> commentDTOList = commentService.findCommentDTOList(id);
        mv.addObject("comments",commentDTOList);
        //阅读数加一
        questionService.insViewCount(id);
        mv.addObject("question",questionDTO);
        mv.setViewName("question");
        return mv;
    }
}
