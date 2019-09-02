package cn.duan.community.model;

import lombok.Data;
import org.apache.lucene.document.FieldType;
import org.jboss.logging.Field;

import javax.persistence.Entity;
//import javax.persistence.Id;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    private Long id;

    private String title;

    private Long gmtCreate;

    private Long gmtModified;

    private Long creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private String description;

    private Long topicId;

}