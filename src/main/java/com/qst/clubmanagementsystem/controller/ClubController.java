package com.qst.clubmanagementsystem.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;
import com.qst.clubmanagementsystem.service.ClubService;
import com.qst.clubmanagementsystem.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);


    // 添加社团
    @PostMapping("/")
    public ResponseEntity<Void> addClub(@RequestParam("club_name") String club_name,
                                        @RequestParam("description") String description,
                                        @RequestParam(value = "club_image", required = false) MultipartFile file) {
        Club club = new Club();
        club.setClub_name(club_name);
        club.setDescription(description);

        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            String image_url = "/static/uploads/" + fileName;  // 生成相对路径
            club.setImage_url(image_url);
        }

        clubService.addClub(club);
        return ResponseEntity.ok().build();
    }

    // 更新社团信息
    @PutMapping("/{club_id}")
    public ResponseEntity<Void> updateClub(@PathVariable int club_id, @RequestBody Club club) {
        club.setClub_id(club_id);
        clubService.updateClub(club);
        return ResponseEntity.ok().build();
    }

    // 删除指定ID的社团
    @DeleteMapping("/{club_id}")
    public ResponseEntity<Void> deleteClub(@PathVariable int club_id) {
        clubService.deleteClub(club_id);
        return ResponseEntity.ok().build();
    }

    // 批量删除社团
    @DeleteMapping("/batch-delete")
    public ResponseEntity<Void> deleteClubs(@RequestBody List<Integer> club_ids) {
        clubService.deleteClubsByIds(club_ids);
        return ResponseEntity.ok().build();
    }

    // 获取所有社团列表
    @GetMapping("/")
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    // 上传社团图片并更新图片URL
    @PostMapping("/{club_id}/uploadImage")
    public ResponseEntity<String> uploadClubImage(@PathVariable int club_id, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String image_url = "/static/uploads/" + fileName;  // 生成相对路径
        clubService.updateClubImage(club_id, image_url);
        return ResponseEntity.ok("Image uploaded successfully: " + image_url);
    }

    // 获取社团成员数量统计
    @GetMapping("/member-counts")
    public ResponseEntity<List<ClubMemberCount>> getClubMemberCounts() {
        List<ClubMemberCount> counts = clubService.getClubMemberCounts();
        return ResponseEntity.ok(counts);
    }

    // 分页查询和模糊搜索社团
    @GetMapping("/page")
    public PageInfo<Club> getClubsByPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "club_name", required = false) String club_name) {
        logger.info("搜索关键字: " + club_name);
        PageHelper.startPage(page, size);
        Page<Club> clubs = clubService.searchClubsByName(club_name);
        return new PageInfo<>(clubs);
    }
    @GetMapping("/{club_id}")
    public ResponseEntity<Club> getClubById(@PathVariable int club_id) {
        Club club = clubService.getClubById(club_id);
        if (club != null) {
            return ResponseEntity.ok(club);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
