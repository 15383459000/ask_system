package com.yunding.answer.controller;

import com.aliyun.oss.OSSClient;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.ImageForm;
import com.yunding.answer.util.OSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author ycSong
 * @version 1.0
 * @date 2019/7/28 17:46
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class OSSController extends BaseController<UserInfoDto> {

    private String bucketName = "yc-song";

    @PostMapping ("/image")
    public ResultWrapper upload(@RequestParam("file") MultipartFile file) throws IOException {

        String userId = getCurrentUserInfo().getUserId();
        //创建oss对象
        OSSClient ossClient = OSSUtil.getOSSClient();
        //转换格式
        File imageFile = OSSUtil.tranFile(file);

        //判断是否为图片，若为图片则上传
        if (OSSUtil.isImage(imageFile)) {

            //图片上传
            OSSUtil.uploadByFile(ossClient, imageFile, bucketName, "image/"+imageFile.getName());
            //删除临时文件
            imageFile.delete();
            log.info(userId+"上传了一张图片，路径为："+"https://yc-song.oss-cn-beijing.aliyuncs.com/image/"+imageFile.getName());
            return ResultWrapper.successWithData("https://yc-song.oss-cn-beijing.aliyuncs.com/image/"+imageFile.getName());
        }

        //删除临时文件
        imageFile.delete();
        return ResultWrapper.failure("请检查您的图片格式是否支持");


    }

    @DeleteMapping("/image")
    public ResultWrapper delete(@RequestBody ImageForm imageForm){

        //创建oss对象
        OSSClient ossClient = OSSUtil.getOSSClient();
        OSSUtil.deleteFile(ossClient,bucketName,imageForm.getImage());
        log.info(imageForm.getImage()+"  被删除了");
        return ResultWrapper.success("请求成功");
    }

}
