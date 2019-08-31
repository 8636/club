package cn.duan.community.controller;

import cn.duan.community.common.utils.AddressUtil;
import cn.duan.community.common.utils.IPUtil;
import cn.duan.community.dto.AccessTokenDTO;
import cn.duan.community.dto.GithubUser;
import cn.duan.community.model.LoginLog;
import cn.duan.community.model.User;
import cn.duan.community.common.provider.GithubProvider;
import cn.duan.community.service.LoginLogService;
import cn.duan.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 授权
 */
@Controller
@Slf4j
public class AuthController extends BaseController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String cclient_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.client.callback}")
    private String callback;

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(cclient_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(callback);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null && githubUser.getId()!=null){
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setBio(githubUser.getBio());
            user.setToken(token);
            this.addLoginLog(user);
            userService.insertOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            response.addCookie(cookie);
            return "redirect:/";
        }else{
            log.error("callback get github error,{}", githubUser);
            return "redirect:/";
        }
    }

    /**
     * 退出
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:";
    }
    private void addLoginLog(User user){
        LoginLog loginLog = new LoginLog();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setUsername(user.getName());
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        loginLog.setDevice(request.getHeader("User-Agent"));
        loginLog.setCreateTime(new Date());
        loginLogService.save(loginLog);
    }
}
