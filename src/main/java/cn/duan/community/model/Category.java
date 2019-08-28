package cn.duan.community.model;

import lombok.Data;

/**
 * 问题类目
 */
@Data
public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private boolean isParent;
    private Integer sort;
}
