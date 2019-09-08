package cn.duan.community.mapper;

import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TopicMapper extends Mapper<Topic> {
    Long findIdByName(String name);

    List<Long> findUserListByTopicId(Long topicId);

    List<User> findUserByIds(List<Long> userIds);

    List<Topic> findnewTopics(Long time);

    List<Topic> findTopicByUserId(Long id);

    int  focusTopic(@Param("id") Long id, @Param("topicId") Long topicId);

    List<Long> listUserTopics(Long id);

    void insLoveCount(Long id);
}
