package cn.duan.community;



import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.QuestionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Autowired
    private QuestionMapper questionMapper;
    @Test
    public void contextLoads() {

    }

}
