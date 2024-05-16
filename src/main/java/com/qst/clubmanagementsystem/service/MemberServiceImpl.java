package com.qst.clubmanagementsystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.clubmanagementsystem.dao.MemberMapper;
import com.qst.clubmanagementsystem.entity.Member;
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
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    @Transactional
    public void addMember(Member member) {
        memberMapper.insertMember(member);
    }

    @Override
    @Transactional
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Override
    @Transactional
    public void deleteMember(int member_id) {
        memberMapper.deleteMember(member_id);
    }

    @Override
    @Transactional
    public void deleteMembersByIds(List<Integer> member_ids) {
        memberMapper.deleteMembers(member_ids);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public List<Member> getMembersByclub_id(int club_id) {
        return memberMapper.selectMembersByclub_id(club_id);
    }

    @Override
    public List<Member> searchMembersByTerm(String searchTerm) {
        return memberMapper.searchMembers(searchTerm);
    }

    @Override
    public PageInfo<Member> getMembersPaginated(int page, int size, String searchTerm, Integer club_id) {
        PageHelper.startPage(page, size);
        List<Member> members;
        if (club_id != null) {
            members = memberMapper.selectMembersByclub_id(club_id);
        } else if (searchTerm != null && !searchTerm.isEmpty()) {
            members = memberMapper.searchMembers(searchTerm);
        } else {
            members = memberMapper.selectAllMembers();
        }
        return new PageInfo<>(members);
    }

    @Override
    public Member getMemberById(int member_id) {
        return memberMapper.getMemberById(member_id);
    }
}