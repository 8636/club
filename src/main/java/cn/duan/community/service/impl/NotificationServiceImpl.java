package cn.duan.community.service.impl;

import cn.duan.community.common.enums.NotificationStatusEnum;
import cn.duan.community.common.enums.NotificationTypeEnum;
import cn.duan.community.mapper.NotificationMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Comment;
import cn.duan.community.model.Notification;
import cn.duan.community.model.User;
import cn.duan.community.service.NotificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     *  创建一个通知
     * @param comment  评论内容
     * @param receiver 接收人
     * @param notifierName 通知人名称
     * @param outerTitle
     * @param notificationType 通知类型  回复问题 or 回复评论
     * @param outerId  所属问题ID
     */
    @Override
    public void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId,String parentContent) {
        //自己回复自己
        if (receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        //如果回复的是评论  插入父评论内容
        notification.setParentContent(parentContent);

        notificationMapper.insert(notification);
    }

    /**
     * 根据用户ID 查询通知
     * @param id
     * @return
     */
    @Override
    public PageInfo<Notification> findListByReceverId(Long id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        Example example = new Example(Notification.class);
        example.createCriteria()
                .andEqualTo("receiver",id);
        example.setOrderByClause("gmt_create");
        List<Notification> notificationList = notificationMapper.selectByExample(example);
        PageInfo<Notification> pageInfo = new PageInfo<>(notificationList);
        return pageInfo;
    }

    /**
     *  阅读通知
     * @param id
     * @return
     */
    @Override
    public Notification read(Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);
        return notification;
    }

    /**
     * 根据用户ID 查询未读通知 数
     * @param id
     * @return
     */
    @Override
    public Long getUnreadCount(Long id) {
        return notificationMapper.getUnreadCountByUserID(id);
    }
}
