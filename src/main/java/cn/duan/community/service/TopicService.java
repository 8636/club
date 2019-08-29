package cn.duan.community.service;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TopicService {

    List<Topic> list();

    Topic findById(Long id);

    Topic findByName(String name);

    Long loveCount(Long id);

    Long questionCount(Long id);

    void save(Topic topic);

    PageInfo<QuestionDTO> findQuestionListByName(String topicName,Integer page,Integer size);

    Long findIdByName(String name);

    List<User> findUserListByTopicId(Long id);
}
