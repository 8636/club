package cn.duan.community.model;

import lombok.Data;

@Data
public class Question {
    /**
     * `id` int(11) NOT NULL AUTO_INCREMENT,
     *   `title` varchar(50) DEFAULT NULL,
     *   `description` text,
     *   `gmt_create` bigint(20) DEFAULT NULL,
     *   `gmt_modified` bigint(20) DEFAULT NULL,
     *   `creator` int(11) DEFAULT NULL,
     *   `comment_count` int(11) DEFAULT '0',
     *   `view_count` int(11) DEFAULT '0',
     *   `like_count` int(11) DEFAULT '0',
     *   `tag` varchar(25
     */

    private int id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;
}
