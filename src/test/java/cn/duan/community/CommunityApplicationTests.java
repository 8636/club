package cn.duan.community;
import cn.duan.community.common.utils.AddressUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Test
    public void contextLoads() {
        String cityInfo = AddressUtil.getCityInfo("223.83.125.104");
        System.out.println(cityInfo);
        System.out.println("------------------------------------");
    }

}
