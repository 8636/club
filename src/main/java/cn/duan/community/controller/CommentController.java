package cn.duan.community.controller;

import cn.duan.community.dto.CommentCreateDTO;
import cn.duan.community.dto.CommentDTO;
import cn.duan.community.dto.ResultDTO;
import cn.duan.community.common.enums.CustomizeErrorCode;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.model.Comment;
import cn.duan.community.model.User;
import cn.duan.community.service.impl.CommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class CommentController {

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    /**
     * 提交评论  可以是问题的评论  和 评论的回复
     *
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @PostMapping("/comment")
    @ResponseBody
    public ResultDTO comment(@RequestBody CommentCreateDTO commentCreateDTO,
                             HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            throw new CustomException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(user.getId());
        commentServiceImpl.save(comment,user);
        log.info("comment", comment);
        return ResultDTO.okOf();
    }

    /**
     * 获得评论下的 回复
     *
     * @param id
     * @return
     */
    @GetMapping("/comment/{id}")
    @ResponseBody
    public ResultDTO<List<CommentDTO>> findCommentsByCommentId(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOList = commentServiceImpl.findCommentDTOListByCommentId(id);
        if (commentDTOList == null || commentDTOList.isEmpty()) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_NOT_FOUND);
        }
        return ResultDTO.okOf(commentDTOList);
    }
}
