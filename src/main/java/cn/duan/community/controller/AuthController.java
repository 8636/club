package cn.duan.community.controller;

import cn.duan.community.dto.AccessTokenDTO;
import cn.duan.community.dto.GithubUser;
import cn.duan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String collback(@RequestParam("code") String code,
                           @RequestParam("state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("42e591d46be31af9c854");
        accessTokenDTO.setClient_secret("ee51a20d4185d65a1bfba0825f94daacaa9b1656");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8888/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
