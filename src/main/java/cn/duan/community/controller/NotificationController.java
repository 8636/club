package cn.duan.community.controller;

import cn.duan.community.common.enums.NotificationTypeEnum;
import cn.duan.community.model.Notification;
import cn.duan.community.model.User;
import cn.duan.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     *  在通知页面实现 点击
     * @return
     */
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:";
        }
        Notification notification =  notificationService.read(id);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notification.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notification.getType()) {
            return "redirect:question/" + notification.getOuterid();
        } else {
            return "redirect:";
        }
    }
}
