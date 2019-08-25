package cn.duan.community.controller;

import cn.duan.community.dto.CommentCreateDTO;
import cn.duan.community.dto.ResultDTO;
import cn.duan.community.model.Comment;
import cn.duan.community.model.User;
import cn.duan.community.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public ResultDTO comment(@RequestBody CommentCreateDTO commentCreateDTO,
                             HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(user.getId());
        commentService.save(comment);
        log.info("comment",comment);
        return ResultDTO.okOf();
    }
}
