package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.service.QuestionDTOService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
public class indexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionDTOService questionService;
    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value = "size",defaultValue = "5") Integer size){

        PageInfo<QuestionDTO> pageInfo = questionService.list(page, size);
        log.info("pageinfo",pageInfo.getList());
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("/index");
        return mv;
    }
}
