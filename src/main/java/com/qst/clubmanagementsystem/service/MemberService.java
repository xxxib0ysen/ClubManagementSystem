package com.qst.clubmanagementsystem.service;

import com.qst.clubmanagementsystem.entity.Member;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */
public interface MemberService {
    void addMember(Member member);
    void updateMember(Member member);
    void deleteMember(int memberId);
    List<Member> getAllMembers();
    List<Member> getMembersByClubId(int clubId);



}
