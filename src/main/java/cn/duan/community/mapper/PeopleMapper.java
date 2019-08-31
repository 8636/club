package cn.duan.community.mapper;

import cn.duan.community.model.Comment;
import cn.duan.community.model.User;
import tk.mybatis.mapper.common.Mapper;

public interface PeopleMapper extends Mapper<User> {
    Comment findLastComment(Long id);
}
