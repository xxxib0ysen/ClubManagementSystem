package com.qst.clubmanagementsystem.service;

import com.github.pagehelper.PageInfo;
import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */

public interface ClubService {
    void addClub(Club club);
    void updateClub(Club club);
    void deleteClub(int club_id);
    void deleteClubsByIds(List<Integer> club_ids);
    List<Club> getAllClubs();
    void updateClubImage(int club_id, String image_url);
    List<ClubMemberCount> getClubMemberCounts();
    PageInfo<Club> getClubsPaginated(int pageNumber, int pageSize, String club_name);
    List<Club> searchClubsByName(String club_name);
    Club getClubById(int club_id);
}
