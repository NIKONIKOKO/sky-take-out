package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.exception.UploadFileFailedException;
import com.sky.service.CommonService;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public String upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            return aliOssUtil.upload(file.getBytes(), newFileName);
        } catch (Exception e) {
            throw new UploadFileFailedException(MessageConstant.UPLOAD_FAILED);
        }
    }
}
