package cn.duan.community.service.impl;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.common.enums.CustomizeErrorCode;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import cn.duan.community.service.QuestionDTOService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionDTOServiceImpl implements QuestionDTOService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获得全部问题 列表
     *
     * @param page
     * @param size
     * @return
     */
    public PageInfo<QuestionDTO> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<QuestionDTO> questionDTOList = questionMapper.selectQuestionDTO(null);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questionDTOList);
        return pageInfo;

    }

    /**
     * 根据用户ID 查询分页查询问题列表
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    public PageInfo<QuestionDTO> findQuestionByUserId(Long id, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<QuestionDTO> questionDTOList = questionMapper.selectQuestionDTO(id);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questionDTOList);
        return pageInfo;
    }


    /**
     * 根据问题ID 进行查询
     *
     * @param id
     * @return
     */
    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            //不存在该问题
            throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 创建或者更新问题
     *
     * @param question
     */
    public void craeteOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        } else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            int i = questionMapper.updateByPrimaryKeySelective(question);
            if (i == 0) {
                //更新失败
                throw new CustomException(CustomizeErrorCode.SYS_ERROR);
            }
        }
    }

    /**
     * 阅读数增加
     *
     * @param id
     */

    public void insViewCount(Long id) {
//        Question question = new Question();
//        question.setId(id);
//        question.setViewCount(1);
//        questionMapper.insViewCount(question);
        questionMapper.insViewCount(id);
    }

    public List<Question> findRelateQuestions(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        String tag = question.getTag();
        String tagStr = StringUtils.replace(tag, ",", "|");
        List<Question> relateQuestions = questionMapper.findRelateQuestions(id, tagStr);
        return relateQuestions;
    }
}
