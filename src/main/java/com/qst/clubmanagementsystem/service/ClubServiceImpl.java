package com.qst.clubmanagementsystem.service;

import com.qst.clubmanagementsystem.dao.ClubMapper;
import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubMapper clubMapper;

    @Override
    @Transactional
    public void addClub(Club club) {
        clubMapper.insertClub(club);
    }

    @Override
    @Transactional
    public void updateClub(Club club) {
        clubMapper.updateClub(club);
    }

    @Override
    @Transactional
    public void deleteClub(int club_id) {
        clubMapper.deleteClub(club_id);
    }

    @Transactional
    @Override
    public void deleteClubsByIds(List<Integer> club_ids) {
        if (club_ids != null && !club_ids.isEmpty()) {
            clubMapper.deleteClubs(club_ids);
        }
    }

    @Override
    public List<Club> getAllClubs() {
        return clubMapper.selectAllClubs();
    }

    @Override
    @Transactional
    public void updateClubImage(int club_id, String image_url) {
        clubMapper.updateClubImage(club_id, image_url);
    }

    @Override
    public List<ClubMemberCount> getClubMemberCounts() {
        return clubMapper.getClubMemberCounts();
    }

    @Override
    public List<Club> getClubsPaginated(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return clubMapper.selectClubsPaginated(offset, pageSize);
    }

    @Override
    public List<Club> searchClubsByTerm(String searchTerm) {
        return clubMapper.searchClubs(searchTerm);
    }
}