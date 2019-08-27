package cn.duan.community.controller;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.NotificationMapper;
import cn.duan.community.model.Notification;
import cn.duan.community.model.User;
import cn.duan.community.service.NotificationService;
import cn.duan.community.service.impl.QuestionDTOServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ProfileController {
    @Autowired
    private QuestionDTOServiceImpl questionService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/profile/{action}")
    public ModelAndView profile(HttpServletRequest request,
                                @PathVariable(name = "action") String action,
                                @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "10") Integer size) {

        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            mv.setViewName("redirect:");
        }

        if ("questions".equals(action)) {
            mv.addObject("section", "questions");
            mv.addObject("sectionName", "我的提问");
            PageInfo<QuestionDTO> pageInfo = questionService.findQuestionByUserId(user.getId(), page, size);
            mv.addObject("pageInfo", pageInfo);
        } else if ("replies".equals(action)) {
            //通知列表
            PageInfo<Notification> pageInfo = notificationService.findListByReceverId(user.getId(), page, size);
            mv.addObject("section", "replies");
            mv.addObject("sectionName", "最新回复");
            mv.addObject("pageInfo", pageInfo);
        }
        mv.setViewName("profile");
        return mv;
    }
}
