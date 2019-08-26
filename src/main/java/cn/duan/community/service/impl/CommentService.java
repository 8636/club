package cn.duan.community.service.impl;

import cn.duan.community.common.enums.NotificationStatusEnum;
import cn.duan.community.common.enums.NotificationTypeEnum;
import cn.duan.community.dto.CommentDTO;
import cn.duan.community.common.enums.CommentTypeEnum;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.common.enums.CustomizeErrorCode;
import cn.duan.community.mapper.CommentMapper;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.model.Comment;
import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import cn.duan.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void save(Comment comment, User commentator) {
        comment.setCommentCount(0);
        comment.setLikeCount(0);
        //评论内容为空
        if (comment.getContent() == null) {
            throw new CustomException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        // 未选中评论
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }//评论 类型错误
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Example example = new Example(Comment.class);
            example.createCriteria()
                    .andEqualTo("id", comment.getParentId());
            List<Comment> dbCommentList = commentMapper.selectByExample(example);
            if (dbCommentList == null || dbCommentList.isEmpty()) {
                throw new CustomException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Long questionId = dbCommentList.get(0).getParentId();
            Question question = questionMapper.selectByPrimaryKey(questionId);
            // 创建通知
            notificationService.createNotify(comment, dbCommentList.get(0).getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
            commentMapper.insCommentCount(dbCommentList.get(0).getId());
            this.saveComment(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            // 创建通知
            notificationService.createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
            this.saveComment(comment);
            this.insCommentCount(question.getId());
        }

    }

    /**
     * 增加评论数
     */
    public void insCommentCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setCommentCount(1);
        questionMapper.insCommentCount(question);
    }

    /**
     * 保存评论
     *
     * @param comment
     * @return
     */
    public void saveComment(Comment comment) {
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        int i = commentMapper.insert(comment);
        if (i == 0) {
            throw new CustomException(CustomizeErrorCode.COMMENT_FAIL);
        }
    }

    /**
     * 查询问题下的评论
     *
     * @param id
     * @return
     */
    public List<CommentDTO> findCommentDTOList(Long id) {
        List<CommentDTO> commentDTOList = commentMapper.findCommentDTOList(id);
        return commentDTOList;
    }

    public List<CommentDTO> findCommentDTOListByCommentId(Long id) {
        List<CommentDTO> commentDTOList = commentMapper.findCommentDTOListByCommentId(id);
        return commentDTOList;
    }

}
