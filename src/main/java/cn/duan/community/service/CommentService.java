package cn.duan.community.service;

import cn.duan.community.enums.CommentTypeEnum;
import cn.duan.community.exception.CustomException;
import cn.duan.community.enums.CustomizeErrorCode;
import cn.duan.community.mapper.CommentMapper;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.model.Comment;
import cn.duan.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    public void save(Comment comment){
        //评论内容为空
        if (comment.getContent() == null){
            throw new CustomException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        // 未选中评论
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }//评论 类型错误
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            this.saveComment(comment);
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            this.saveComment(comment);
            this.insCommentCount(question.getId());
        }

    }


    /**
     * 增加评论数
     */
    public void insCommentCount(Long id){
        Question question = new Question();
        question.setId(id);
        question.setCommentCount(1);
        questionMapper.insCommentCount(question);
    }

    /**
     * 保存评论
     * @param comment
     * @return
     */
    public void saveComment(Comment comment){
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        int i = commentMapper.insert(comment);
        if (i == 0){
            throw new CustomException(CustomizeErrorCode.COMMENT_FAIL);
        }
    }
}
