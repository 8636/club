package cn.duan.community.service.impl;

import cn.duan.community.common.enums.ExceptionEnum;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.dto.QuestionQueryDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.TopicMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import cn.duan.community.service.TopicService;
import cn.duan.community.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<Topic> list() {
        List<Topic> topicList = topicMapper.selectAll();
        return topicList;
    }

    @Override
    public PageInfo<QuestionDTO> findQuestionListByName(String topicName, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        Long topicId = findIdByName(topicName);
        questionQueryDTO.setTopicId(topicId);
        List<QuestionDTO> questionDTOList = questionMapper.selectQuestionDTO(questionQueryDTO);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<QuestionDTO>(questionDTOList);
        return pageInfo;
    }

    @Override
    public Long findIdByName(String name) {
        Long topicId = topicMapper.findIdByName(name);
        if (topicId == null) {
            return null;
        }
        return topicId;

    }

    @Override
    public Topic findById(Long id) {
        return null;
    }

    @Override
    public Topic findByName(String name) {
        Example example = new Example(Topic.class);
        example.createCriteria()
                .andEqualTo("name", name);
        List<Topic> topicList = topicMapper.selectByExample(example);
        if (topicList == null || topicList.size() == 0) {
            return null;
        }
        return topicList.get(0);
    }

    @Override
    public Long loveCount(Long id) {
        return null;
    }

    @Override
    public Long questionCount(Long id) {
        return null;
    }

    @Override
    public void save(Topic topic) {

    }

    @Override
    public List<User> findUserListByTopicId(Long id) {
        List<Long> userIds = topicMapper.findUserListByTopicId(id);
        List<User> userList = topicMapper.findUserByIds(userIds);
        return userList;

    }

    /**
     * 查询 新创建的话题
     *
     * @return
     */
    @Override
    public List<Topic> newTopics() {

        //当前时间减去三天
        Long time = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 3;
        topicMapper.findnewTopics(time);
        return null;
    }

    /**
     * 查询用户关注的话题
     *
     * @param username
     * @return
     */
    @Override
    public List<Topic> findTopicByUserName(String username) {
        User user = userService.findUserByName(username);
        List<Topic> topicList = topicMapper.findTopicByUserId(user.getId());
        return topicList;
    }

    /**
     * 关注问题
     *
     * @param id
     * @param topicId
     */
    @Override
    @Transactional
    public int focusTopic(Long id, Long topicId) {
        topicMapper.insLoveCount(topicId);
        return topicMapper.focusTopic(id, topicId);
    }
}
