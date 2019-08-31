package cn.duan.community.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_topic")
public class Topic {

    @Id
    private Long id;
    private String name;
    private String decription;
    private Long parentId;
    private Integer commentCount;
    private Integer loveCount;
    private String avatarUrl;
    private Long gmtCreate;
    private Long gmtModified;

}
