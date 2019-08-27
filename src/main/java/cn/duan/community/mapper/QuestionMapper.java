package cn.duan.community.mapper;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.dto.QuestionQueryDTO;
import cn.duan.community.model.Question;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QuestionMapper extends Mapper<Question> {

    List<QuestionDTO> selectQuestionDTO(QuestionQueryDTO questionQueryDTO);

//    void insViewCount(Question question);
    void insViewCount(@Param("id") Long id);

    List<Question> find();

    List<Question> findQuestionsByCreator(@Param("creator") Long id);

    void insCommentCount(Question question);

    List<Question> findRelateQuestions(@Param("id") Long id,@Param("tag") String str);
}