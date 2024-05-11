package com.qst.clubmanagementsystem.controller;

import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.service.ClubService;
import com.qst.clubmanagementsystem.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */

@RestController
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    private ClubService clubService;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/")
    public void addClub(@RequestBody Club club) {
        clubService.addClub(club);
    }

    @PutMapping("/{clubId}")
    public void updateClub(@PathVariable int clubId, @RequestBody Club club) {
        club.setClubId(clubId);
        clubService.updateClub(club);
    }

    @DeleteMapping("/{clubId}")
    public void deleteClub(@PathVariable int clubId) {
        clubService.deleteClub(clubId);
    }

    @GetMapping("/")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    // 上传社团图像并更新图像URL
    @PostMapping("/{clubId}/uploadImage")
    public String uploadClubImage(@PathVariable int clubId, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String imageUrl = "/uploads/" + fileName;
        clubService.updateClubImage(clubId, imageUrl);
        return "Image uploaded successfully: " + imageUrl;
    }

}