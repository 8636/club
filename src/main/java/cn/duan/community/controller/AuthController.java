package cn.duan.community.controller;

import cn.duan.community.dto.AccessTokenDTO;
import cn.duan.community.dto.GithubUser;
import cn.duan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String cclient_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.client.callback}")
    private String callback;
    @GetMapping("/callback")
    public String collback(@RequestParam("code") String code,
                           @RequestParam("state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(cclient_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(callback);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
