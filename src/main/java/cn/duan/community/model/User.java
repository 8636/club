package cn.duan.community.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;

    private String accountId;

    private String token;

    private Long gmtCreate;

    private Long gmtModified;

    private String name;

    private String bio;

    private String avatarUrl;

}