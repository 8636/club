package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.service.impl.QuestionDTOServiceImpl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Id;

@Controller
@Slf4j
public class indexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionDTOServiceImpl questionService;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              @RequestParam(value = "search",required = false) String search) {
        if (search!= null && search != ""){
            search = StringUtils.lowerCase(search).trim().replace(" ","|");
        }
        PageInfo<QuestionDTO> pageInfo = questionService.list(page, size,search);
        log.info("pageinfo", pageInfo.getList());
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("search",search);
        mv.setViewName("/index");
        return mv;
    }
}
