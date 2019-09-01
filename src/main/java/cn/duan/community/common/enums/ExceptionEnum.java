package cn.duan.community.common.enums;

import lombok.Getter;

/**
 *  自定义异常枚举
 */
@Getter
public enum ExceptionEnum{

    QUESTION_NOT_FOUND(2001, "问题不存在或者已删除"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    COMMENT_FAIL(2013,"评论失败"),
    USER_NOT_FOUND(2014,"用户不存在"),
    FOCUS_TOPIC_FAIL(2015,"关注话题失败"),
    ;

     Integer code;

     String message;

    ExceptionEnum(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}