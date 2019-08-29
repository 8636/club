package cn.duan.community.service;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class FileUploadService {

    @Value("${image.adress}")
    private String imageAdress;
    @Autowired
    private FastFileStorageClient storageClient;
    private final List<String> allowTypes = Arrays.asList("jpg","png","jpeg","gif","image/jpeg");
    public String uploadImg(MultipartFile file){
        try{
            // 1、文件的校验
            // 1.1.校验文件类型
            String contentType = file.getContentType();
            if(!allowTypes.contains(contentType)){
                // 类型不匹配，直接返回null
                return null;
            }
            // 1.2.校验数据内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                // 内容有问题
                return null;
            }
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(),getExtension(file.getOriginalFilename()), null);
            // 3、生成图片地址
            String url =  imageAdress + storePath.getFullPath();
            log.info("图片上传成功：文件名：{}", file.getOriginalFilename());
            return url;
        } catch (Exception e){
            log.error("文件上传失败：文件名：{}", file.getOriginalFilename(), e);
            return null;
        }
    }
    public String getExtension(String fileName){
        return StringUtils.substringAfterLast(fileName,".");
    }

}
