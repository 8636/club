package cn.duan.community.service;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.model.Question;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QuestionDTOService {

     PageInfo<QuestionDTO> list(Integer page, Integer size, String search,String tag,String sortStr);

     PageInfo<QuestionDTO> findQuestionByUserId(Long id, Integer page, Integer size);

     QuestionDTO getById(Long id);

     void craeteOrUpdate(Question question);

     void insViewCount(Long id);

     List<Question> findRelateQuestions(Long id);
}

