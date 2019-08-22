package cn.duan.community.provider;


import cn.duan.community.dto.AccessTokenDTO;
import cn.duan.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //  access_token=48c240d89926dc728ec87712277a1fd4f15669e1&scope=user&token_type=bearer
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        }
    }


    public GithubUser getUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){

        }
        return null;
    }
}
