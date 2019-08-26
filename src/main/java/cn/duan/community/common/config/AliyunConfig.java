package cn.duan.community.common.config;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties(prefix = "aliyun")
@Slf4j
@Data
public class AliyunConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String urlPrefix;

    @Bean
    public OSSClient getOssClient(){
        //String endpoint, CredentialsProvider credsProvider, ClientConfiguration config
        log.info("enpoint",endpoint);
        return new OSSClient(endpoint,accessKeyId,accessKeySecret);

    }
}
