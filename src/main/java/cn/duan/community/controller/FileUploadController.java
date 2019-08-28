package cn.duan.community.controller;

import cn.duan.community.dto.UploadDTO;
import cn.duan.community.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {


    @Autowired
    private FileUploadService fileUploadService;
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
        String img = fileUploadService.uploadImg(file);
        if (img == null || img == ""){
            uploadDTO.setMessage("上传失败");
            uploadDTO.setSuccess(0);
            return uploadDTO;
        }
        uploadDTO.setUrl(img);
        uploadDTO.setSuccess(1);
        uploadDTO.setMessage("上传图片成功");
        return uploadDTO;
    }

}
