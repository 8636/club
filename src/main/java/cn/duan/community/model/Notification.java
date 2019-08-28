package cn.duan.community.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Notification {

    @Id
    private Long id;

    /**
     * 发送方
     */
    private Long notifier;

    /**
     * 接收方
     */
    private Long receiver;

    /**
     * 外键id
     */
    private Long outerid;

    /**
     * 类型 评论问题 or 回复评论
     */
    private Integer type;

    private Long gmtCreate;

    private Integer status;

    private String notifierName;

    private String outerTitle;

    private String parentContent;
}