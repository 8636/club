package cn.duan.community.common.exception;


import cn.duan.community.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * 继承RuntimeException  别人在调用该方法时 不用去 try catch
 */
@Data
public class CustomException extends RuntimeException {

    private String message;

    private Integer code;

    public CustomException(ExceptionEnum exceptionEnum) {
        this.message = exceptionEnum.getMessage();
        this.code = exceptionEnum.getCode();
    }
}
