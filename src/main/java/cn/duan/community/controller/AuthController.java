package cn.duan.community.controller;

import cn.duan.community.dto.AccessTokenDTO;
import cn.duan.community.dto.GithubUser;
import cn.duan.community.model.User;
import cn.duan.community.common.provider.GithubProvider;
import cn.duan.community.service.UserService;
import cn.duan.community.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 授权
 */
@Controller
public class

AuthController {

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

    @GetMapping("/callback")
    public String collback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(cclient_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(callback);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser.getId() == null){
            return "redirect:";
        }
        User user = new User();
        user.setName(githubUser.getName());
        user.setAccountId(String.valueOf(githubUser.getId()));
        String token = UUID.randomUUID().toString();
        user.setAvatarUrl(githubUser.getAvatarUrl());
        user.setToken(token);
        userService.insertOrUpdate(user);
        if (githubUser != null) {
            //获得用户信息，保存到session中
            response.addCookie(new Cookie("token", token));
            return "redirect:";
        } else {
            return "redirect:";
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

}
