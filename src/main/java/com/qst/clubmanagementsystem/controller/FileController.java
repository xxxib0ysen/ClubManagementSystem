package com.qst.clubmanagementsystem.controller;

import com.qst.clubmanagementsystem.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description 文件上传和下载的控制器
 */
@RestController
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 上传文件的端点
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 调用服务存储文件
            String fileName = fileStorageService.storeFile(file);
            // 生成文件下载的URI
            String fileDownloadUri = "/uploads/" + fileName;

            return ResponseEntity.ok(fileDownloadUri);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("图片上传失败：" + e.getMessage());
        }
    }

    // 下载文件的端点
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            // 获取文件路径
            Path file = Paths.get(uploadDir).resolve(filename);
            // 生成资源对象
            Resource resource = new UrlResource(file.toUri());

            // 判断文件是否存在且可读
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("无法读取文件：" + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("错误：" + e.getMessage());
        }
    }
}
