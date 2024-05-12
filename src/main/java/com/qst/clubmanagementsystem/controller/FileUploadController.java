package com.qst.clubmanagementsystem.controller;

import com.qst.clubmanagementsystem.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/7
 */

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;

    // 上传文件并返回文件存储的URL
    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        return "File uploaded successfully: " + fileName;
    }
}
