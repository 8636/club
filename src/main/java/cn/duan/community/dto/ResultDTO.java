package cn.duan.community.dto;

import cn.duan.community.common.exception.CustomException;
import cn.duan.community.common.enums.CustomizeErrorCode;
import lombok.Data;

/**
 * 返回结果
 * @param <T>
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    T data;


    private static ResultDTO errorOf(Integer code ,String  message){
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

}
