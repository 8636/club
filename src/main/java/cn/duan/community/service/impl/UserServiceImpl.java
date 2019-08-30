package cn.duan.community.service.impl;


import cn.duan.community.common.enums.CustomizeErrorCode;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.mapper.PeopleMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Comment;
import cn.duan.community.model.User;
import cn.duan.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PeopleMapper peopleMapper;

    /**
     * 根据session中的 user进行查询  数据库没有就插入  有就更新
     *
     * @param user
     */
    public void insertOrUpdate(User user) {
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("accountId", user.getAccountId());
        List<User> userList = userMapper.selectByExample(example);
        if (userList.size() == 0) {
            //添加
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        } else {
            //更新
            User dbUser = userList.get(0);
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.updateByPrimaryKeySelective(dbUser);
        }
    }

    @Override
    public User findUserById(Long id) {
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("id", id);
        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.size() == 0) {
            throw new CustomException(CustomizeErrorCode.USER_NOT_FOUND);
        }
        return users.get(0);
    }

    @Override
    public User findUserByName(String username) {
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("name", username);
        List<User> userList = userMapper.selectByExample(example);
        if (userList == null || userList.size() == 0) {
            throw new CustomException(CustomizeErrorCode.USER_NOT_FOUND);
        }
        return userList.get(0);
    }

    /**
     * 查询用户最新的一条回复
     *
     * @param id
     * @return
     */
    @Override
    public Comment findLastComment(Long id) {
        Comment lastComment = peopleMapper.findLastComment(id);
        return lastComment;
    }
}
