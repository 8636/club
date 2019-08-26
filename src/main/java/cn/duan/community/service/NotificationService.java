package cn.duan.community.service;


import cn.duan.community.common.enums.NotificationTypeEnum;
import cn.duan.community.model.Comment;
import cn.duan.community.model.Notification;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    //创建一个通知
    void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId);

    //查询用户的通知
    PageInfo<Notification> findListByReceverId(Long id, Integer page, Integer size);

    Notification read(Long id);

    Long getUnreadCount(Long id);
}
