package cn.duan.community.service.impl;

import cn.duan.community.common.enums.SortEnum;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.common.enums.CustomizeErrorCode;
import cn.duan.community.dto.QuestionQueryDTO;
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

import java.io.IOException;
import java.util.List;


/**
 * @author Sir
 */
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
    @Override
    public PageInfo<QuestionDTO> list(Integer page, Integer size, String search, String tag, String sort) throws IOException {

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        if (search != null && search != "") {
            search = StringUtils.lowerCase(search).trim().replace(" ", "|");
        }
        questionQueryDTO.setSearch(search);
        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);
                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }
        questionQueryDTO.setTag(tag);
        PageHelper.startPage(page, size);
        List<QuestionDTO> questionDTOList = questionMapper.selectQuestionDTO(questionQueryDTO);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<QuestionDTO>(questionDTOList);
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
    @Override
    public PageInfo<QuestionDTO> findQuestionByUserId(Long id, Integer page, Integer size) {
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setUserId(id);
        PageHelper.startPage(page, size);
        List<QuestionDTO> questionDTOList = questionMapper.selectQuestionDTO(questionQueryDTO);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questionDTOList);
        return pageInfo;
    }


    /**
     * 根据问题ID 进行查询
     *
     * @param id
     * @return
     */
    @Override
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
    @Override
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

    @Override
    public void insViewCount(Long id) {

        questionMapper.insViewCount(id);
    }

    @Override
    public List<Question> findRelateQuestions(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        String tag = question.getTag();
        String tagStr = StringUtils.replace(tag, ",", "|");
        List<Question> relateQuestions = questionMapper.findRelateQuestions(id, tagStr);
        return relateQuestions;
    }
}
