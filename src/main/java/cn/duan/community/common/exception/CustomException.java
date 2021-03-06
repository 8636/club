package cn.duan.community.common.exception;


import cn.duan.community.common.ResultCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 继承RuntimeException  别人在调用该方法时 不用去 try catch
 */
@Data
public class CustomException extends RuntimeException {

    private String message;

    private Integer code;

    public CustomException(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }
}
