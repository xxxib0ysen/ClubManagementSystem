package com.qst.clubmanagementsystem.controller;

import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;
import com.qst.clubmanagementsystem.service.ClubService;
import com.qst.clubmanagementsystem.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // 添加社团
    @PostMapping("/")
    public void addClub(@RequestBody Club club) {
        clubService.addClub(club);
    }

    // 更新社团信息
    @PutMapping("/{club_id}")
    public void updateClub(@PathVariable int club_id, @RequestBody Club club) {
        club.setClub_id(club_id);
        clubService.updateClub(club);
    }

    // 删除指定ID的社团
    @DeleteMapping("/{club_id}")
    public void deleteClub(@PathVariable int club_id) {
        clubService.deleteClub(club_id);
    }

    // 批量删除社团
    @DeleteMapping("/batch-delete")
    public ResponseEntity<Void> deleteClubs(@RequestBody List<Integer> club_ids) {
        clubService.deleteClubsByIds(club_ids);
        return ResponseEntity.ok().build();
    }

    // 获取所有社团列表
    @GetMapping("/")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    // 上传社团图片并更新图片URL
    @PostMapping("/{club_id}/uploadImage")
    public String uploadClubImage(@PathVariable int club_id, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String image_url = "/uploads/" + fileName;
        clubService.updateClubImage(club_id, image_url);
        return "Image uploaded successfully: " + image_url;
    }

    // 获取社团成员数量统计
    @GetMapping("/member-counts")
    public List<ClubMemberCount> getClubMemberCounts() {
        return clubService.getClubMemberCounts();
    }

    // 分页查询社团
    @GetMapping("/page")
    public List<Club> getClubsPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return clubService.getClubsPaginated(page, size);
    }

    // 模糊搜索社团
    @GetMapping("/search")
    public List<Club> searchClubs(@RequestParam("term") String searchTerm) {
        return clubService.searchClubsByTerm(searchTerm);
    }
}