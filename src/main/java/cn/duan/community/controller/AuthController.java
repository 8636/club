package cn.duan.community.controller;

import cn.duan.community.dto.AccessTokenDTO;
import cn.duan.community.dto.GithubUser;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.User;
import cn.duan.community.provider.GithubProvider;
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
 *  授权
 */
@Controller
public class AuthController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
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

        User user = new User();
        user.setName(githubUser.getName());
        user.setAccountId(String.valueOf(githubUser.getId()));
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModetife(System.currentTimeMillis());
        userMapper.add(user);
        if (githubUser != null) {
            //获得用户信息，保存到session中
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
