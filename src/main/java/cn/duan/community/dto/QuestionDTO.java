package cn.duan.community.dto;

import cn.duan.community.model.User;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
@Data
public class QuestionDTO {

    @Id
    private Long id;

    private String description;

    private String title;

    private Long gmtCreate;

    private Long gmtModified;

    private Long creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    @Transient
    private User user;
}
