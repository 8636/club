package cn.duan.community.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModetife;
}
