package cn.duan.community.dto;

import cn.duan.community.model.User;
import lombok.Data;

import javax.persistence.Id;

@Data
public class CommentDTO {
    @Id
    private Long id;
    private Long parentId;

    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private User user;
}
