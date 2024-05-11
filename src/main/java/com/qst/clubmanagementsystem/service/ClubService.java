package com.qst.clubmanagementsystem.service;

import com.qst.clubmanagementsystem.entity.Club;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */

public interface ClubService {
    void addClub(Club club);
    void updateClub(Club club);
    void deleteClub(int clubId);
    List<Club> getAllClubs();
    // 更新社团的图片URL
    void updateClubImage(int clubId, String imageUrl);

}
