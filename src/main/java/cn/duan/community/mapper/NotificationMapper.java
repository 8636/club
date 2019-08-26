package cn.duan.community.mapper;


import cn.duan.community.model.Notification;
import tk.mybatis.mapper.common.Mapper;

public interface NotificationMapper extends Mapper<Notification> {

    Long getUnreadCountByUserID(Long id);
}
