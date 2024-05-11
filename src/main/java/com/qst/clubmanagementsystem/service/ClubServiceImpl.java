package com.qst.clubmanagementsystem.service;

import com.qst.clubmanagementsystem.dao.ClubMapper;
import com.qst.clubmanagementsystem.entity.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addClub(Club club) {
        clubMapper.insertClub(club);
    }

    @Override
    public void updateClub(Club club) {
        clubMapper.updateClub(club);
    }

    @Override
    public void deleteClub(int clubId) {
        clubMapper.deleteClub(clubId);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubMapper.selectAllClubs();
    }

    @Override
    public void updateClubImage(int clubId, String imageUrl) {
        clubMapper.updateClubImage(clubId, imageUrl);
    }
}
