package com.qst.clubmanagementsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Description 处理文件存储的服务
 * @Author xxxib0ysen
 * @Date 2024/5/7
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;


    public FileStorageService() {
        // 将存储路径设为绝对路径并标准化
        this.fileStorageLocation = Paths.get("C:\\Java\\ClubManagementSystem\\uploads").toAbsolutePath().normalize();
        try {
            // 如果目录不存在，则创建目录
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("无法创建存储上传文件的目录。", ex);
        }
    }

    // 存储文件的方法
    public String storeFile(MultipartFile file) {
        try {
            // 获取原始文件名
            String fileName = file.getOriginalFilename();
            // 生成目标路径
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // 将文件复制到目标位置，如果文件已存在则替换
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception ex) {
            throw new RuntimeException("无法存储文件 " + file.getOriginalFilename() + "。请重试！", ex);
        }
    }
}
