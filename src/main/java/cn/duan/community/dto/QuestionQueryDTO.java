package cn.duan.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {

    private Long userId;
    private String search;
    private String sort;
    private Long time;
    private String tag;
    private Long topicId;
//    private Integer page;
//    private Integer size;
}