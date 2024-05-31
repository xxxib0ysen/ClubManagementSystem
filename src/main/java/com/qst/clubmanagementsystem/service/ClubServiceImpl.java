package com.qst.clubmanagementsystem.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Override
    @Transactional
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
        Club club = clubMapper.getClubById(club_id);
        if (club != null) {
            club.setImage_url(image_url);
            clubMapper.updateClubImage(club_id, image_url);
        } else {
            throw new RuntimeException("Club not found with id: " + club_id);
        }
    }

    @Override
    public List<ClubMemberCount> getClubMemberCounts() {
        return clubMapper.getClubMemberCounts();
    }

    @Override
    public PageInfo<Club> getClubsPaginated(int pageNumber, int pageSize, String club_name) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Club> clubs;
        if (club_name == null || club_name.isEmpty()) {
            clubs = clubMapper.selectAllClubs();
        } else {
            clubs = clubMapper.searchClubsByName(club_name);
        }
        return new PageInfo<>(clubs);
    }

    @Override
    public Page<Club> searchClubsByName(String club_name) {
        return clubMapper.searchClubsByName(club_name);
    }

    @Override
    public Club getClubById(int club_id) {
        return clubMapper.getClubById(club_id);
    }
}
