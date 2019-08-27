package cn.duan.community.service;

import cn.duan.community.dto.CommentDTO;
import cn.duan.community.model.Comment;
import cn.duan.community.model.User;

import java.util.List;

public interface CommentService {

    void save(Comment comment, User commentator);

    void insCommentCount(Long id);

    void saveComment(Comment comment);

    List<CommentDTO> findCommentDTOList(Long id);

    List<CommentDTO> findCommentDTOListByCommentId(Long id);
}
