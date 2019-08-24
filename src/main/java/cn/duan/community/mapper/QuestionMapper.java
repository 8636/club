package cn.duan.community.mapper;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.model.Question;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QuestionMapper extends Mapper<Question> {

    List<QuestionDTO> selectQuestionDTO(@Param("id") Integer id);

    void insViewCount(Question question);

    List<Question> find();

    List<Question> findQuestionsByCreator(@Param("creator") Integer id);
}