package com.qst.clubmanagementsystem.service;

import com.qst.clubmanagementsystem.dao.MemberMapper;
import com.qst.clubmanagementsystem.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void addMember(Member member) {
        memberMapper.insertMember(member);
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Override
    public void deleteMember(int memberId) {
        memberMapper.deleteMember(memberId);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public List<Member> getMembersByClubId(int clubId) {
        return memberMapper.selectMembersByClubId(clubId);
    }
}
