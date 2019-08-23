package cn.duan.community.controller;

import cn.duan.community.dto.PaginationDTO;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.model.User;
import cn.duan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;


    @GetMapping("/profile/{action}")
    public ModelAndView profile(HttpServletRequest request,
                                @PathVariable(name = "action") String action,
                                @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "5") Integer size) {

        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            mv.setViewName("redirect:/");
        }

        if ("questions".equals(action)) {
            mv.addObject("section", "questions");
            mv.addObject("sectionName", "我的提问");
            PaginationDTO<QuestionDTO> pagination = questionService.findQuestionById(user.getId(),page,size);
            mv.addObject("pagination", pagination);
        } else if ("replies".equals(action)) {
//            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            mv.addObject("section", "replies");
//            mv.addObject("pagination", paginationDTO);
            mv.addObject("sectionName", "最新回复");
        }
        mv.setViewName("/profile");
        return mv;
    }
}
