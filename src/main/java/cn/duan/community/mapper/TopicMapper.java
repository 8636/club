package cn.duan.community.mapper;

import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TopicMapper extends Mapper<Topic> {
    Long findIdByName(String name);

    List<Long> findUserListByTopicId(Long topicId);

    List<User> findUserByIds(List<Long> userIds);
}
