package cn.duan.community.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    ModelAndView handle(Throwable e,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        if (e instanceof CustomException){
            mv.addObject("message","错误了");
        }
        mv.setViewName("/error");
        return mv;
    }
}
