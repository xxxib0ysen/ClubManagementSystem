package com.qst.clubmanagementsystem.service;

import com.github.pagehelper.PageInfo;
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
    void deleteMember(int member_id);
    void deleteMembersByIds(List<Integer> member_ids);
    List<Member> getAllMembers();
    List<Member> getMembersByclub_id(int club_id);
    List<Member> searchMembersByTerm(String searchTerm);
    PageInfo<Member> getMembersPaginated(int page, int size, String searchTerm);
    Member getMemberById(int memberId);
}