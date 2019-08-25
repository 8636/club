package cn.duan.community.dto;

import lombok.Data;

/**
 * 页面传来的评论内容
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}