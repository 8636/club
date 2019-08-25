package cn.duan.community.mapper;

import cn.duan.community.dto.CommentDTO;
import cn.duan.community.model.Comment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment> {

    List<CommentDTO> findCommentDTOList(@Param("id") Long id);
}
