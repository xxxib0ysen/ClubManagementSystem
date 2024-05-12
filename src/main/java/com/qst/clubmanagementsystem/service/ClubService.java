package com.qst.clubmanagementsystem.service;

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
    void deleteClub(int clubId);
    List<Club> getAllClubs();
    void updateClubImage(int clubId, String imageUrl);
    List<ClubMemberCount> getClubMemberCounts();
    List<Club> getClubsPaginated(int pageNumber, int pageSize);
    List<Club> searchClubsByTerm(String searchTerm);
}
