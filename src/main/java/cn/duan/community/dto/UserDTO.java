package cn.duan.community.dto;

import cn.duan.community.model.Question;
import cn.duan.community.model.Topic;
import cn.duan.community.model.User;
import lombok.Data;

import java.util.List;

/**
 * 用户主页 数据
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private List<Question> questionList;
    private List<Topic> topicList;
    private List<CommentDTO> commentDTOList;
    private List<User> userList;
    private int viewCount;
}
