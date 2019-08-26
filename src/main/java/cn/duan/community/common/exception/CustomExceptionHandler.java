package cn.duan.community.common.exception;

import cn.duan.community.dto.ResultDTO;
import cn.duan.community.common.enums.CustomizeErrorCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    ModelAndView handle(Throwable e,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            // 返回 JSON
            if (e instanceof CustomException) {
                resultDTO = ResultDTO.errorOf((CustomException) e);
            } else {
                log.error("handle error", e);
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        }else {
            // 错误页面跳转
            if (e instanceof CustomException) {
                mv.addObject("message", e.getMessage());
            } else {
                log.error("handle error", e);
                mv.addObject("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
