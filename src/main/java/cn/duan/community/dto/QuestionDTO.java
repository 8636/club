package cn.duan.community.dto;

import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO extends Question {
    private User user;
}
