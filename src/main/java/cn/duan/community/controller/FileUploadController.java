package cn.duan.community.controller;

import cn.duan.community.common.enums.CustomizeErrorCode;
import cn.duan.community.common.exception.CustomException;
import cn.duan.community.dto.UploadDTO;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUploadController {

    @Autowired
    private OSSClient ossClient;

    @Value("${aliyun.urlPrefix}")
    private String urlPrefix;

    @Value("${aliyun.bucketName}")
    private String bucketName;

    @PostMapping("/file/upload")
    @ResponseBody
    public UploadDTO uploadImage(@RequestParam("guid") Long guid,
                                 @RequestParam("editormd-image-file") MultipartFile file) {
        UploadDTO uploadDTO = new UploadDTO();
        if (file == null){
            uploadDTO.setMessage("图片为空");
            uploadDTO.setSuccess(0);
            return uploadDTO;
        }

        // 文件新路径
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        try {
            ossClient.putObject("communitypicture", fileName, file.getInputStream());
            uploadDTO.setUrl(urlPrefix + fileName);
        } catch (IOException e) {
            throw new CustomException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        uploadDTO.setSuccess(1);
        uploadDTO.setMessage("上传图片成功");
        return uploadDTO;
    }

}
