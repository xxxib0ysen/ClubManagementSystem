package com.qst.clubmanagementsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/7
 */

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("src/main/resources/static/uploads").toAbsolutePath().normalize();

    public FileStorageService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);
            return fileName;
        } catch (Exception ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
