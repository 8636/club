package cn.duan.community.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Comment {

    @Id
    private Long id;

    /**
     *  父ID
     */
    private Long parentId;

    /**
     *  问题的评论  还是评论的 回复
     */
    private Integer type;

    /**
     * 评论人
     */
    private Long commentator;

    /**
     * 评论内容
     */
    private String content;

    private Long gmtCreate;


    private Long gmtModified;


    private Long likeCount;



    private Integer commentCount;
}