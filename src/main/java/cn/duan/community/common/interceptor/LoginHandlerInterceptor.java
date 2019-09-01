package cn.duan.community.common.interceptor;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.User;
import cn.duan.community.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入拦截器");
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    Example example = new Example(User.class);
                    example.createCriteria()
                            .andEqualTo("token",cookie.getValue());
                    List<User> userList = userMapper.selectByExample(example);
                    if (userList.size() != 0) {
                        request.getSession().setAttribute("user", userList.get(0));
                        Long unreadCount = notificationService.getUnreadCount(userList.get(0).getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                        log.info("sessionID {}",request.getSession().getId());
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
