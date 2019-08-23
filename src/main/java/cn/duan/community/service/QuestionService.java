package cn.duan.community.service;

import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> list = questionMapper.list();
        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            int creator = question.getCreator();

            User user = userMapper.findById(creator);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
