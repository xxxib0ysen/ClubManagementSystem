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
 * @Description 社团管理控制器
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
    public ResponseEntity<Club> addClub(@RequestParam("club_name") String club_name,
                                        @RequestParam("description") String description,
                                        @RequestParam(value = "club_image", required = false) MultipartFile file) {
        Club club = new Club();
        club.setClub_name(club_name);
        club.setDescription(description);

        // 如果有文件上传，则存储文件并设置图片URL
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            String image_url = "/uploads/" + fileName;  // 生成相对路径
            club.setImage_url(image_url);
        }

        clubService.addClub(club);
        return ResponseEntity.ok(club);
    }

    // 更新社团信息
    @PutMapping("/{club_id}")
    public ResponseEntity<Club> updateClub(@PathVariable int club_id,
                                           @RequestParam("club_name") String club_name,
                                           @RequestParam("description") String description,
                                           @RequestParam(value = "club_image", required = false) MultipartFile file,
                                           @RequestParam(value = "existing_image_url", required = false) String existingImageUrl) {
        Club club = new Club();
        club.setClub_id(club_id);
        club.setClub_name(club_name);
        club.setDescription(description);

        // 如果有新的文件上传，则存储新文件并设置新的图片URL，否则使用现有的图片URL
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            String image_url = "/uploads/" + fileName;  // 生成相对路径
            club.setImage_url(image_url);
        } else {
            club.setImage_url(existingImageUrl);
        }

        clubService.updateClub(club);
        return ResponseEntity.ok(club);
    }

    // 更新社团图片
    @PutMapping("/{club_id}/updateImage")
    public ResponseEntity<Void> updateClubImage(@PathVariable int club_id, @RequestParam("club_image") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            String image_url = "/uploads/" + fileName;  // 生成相对路径
            clubService.updateClubImage(club_id, image_url);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
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
