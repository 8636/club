package cn.duan.community.service;

import cn.duan.community.model.User;

import java.util.List;

public interface UserService {
    void insertOrUpdate(User user);

    User findUserById(Long id);

    User findUserByName(String username);

//    List<User> findUserListByTopicId(Long topicId);


}
