package cn.duan.community.service;


import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

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
}
