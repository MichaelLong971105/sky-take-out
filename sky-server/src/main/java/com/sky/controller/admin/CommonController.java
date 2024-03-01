package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @program: sky-take-out-backend
 * @description: 通用Controller接口
 * @author: MichaelLong
 * @create: 2024-03-01 21:54
 **/
@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {

    @Autowired
    AliOssUtil aliOssUtil;

    /**
     * @Description: 文件上传功能
     * @Param: [file]
     * @return: com.sky.result.Result<java.lang.String>
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传: {}", file);
        try {
            String originalFilename = file.getOriginalFilename(); //获取原始文件名
            //截取原始文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新的文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            //获取文件访问路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败{}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
