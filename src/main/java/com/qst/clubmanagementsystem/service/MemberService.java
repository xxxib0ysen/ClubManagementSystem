package com.qst.clubmanagementsystem.service;

import com.github.pagehelper.PageInfo;
import com.qst.clubmanagementsystem.entity.Member;

import java.util.List;

/**
 * 会员管理服务接口，定义了对会员信息的操作。
 *
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */
public interface MemberService {

    /**
     * 向系统中添加新的会员。
     *
     * @param member 要添加的会员对象
     */
    void addMember(Member member);

    /**
     * 更新系统中的会员信息。
     *
     * @param member 包含更新信息的会员对象
     */
    void updateMember(Member member);

    /**
     * 根据会员ID删除系统中的会员信息。
     *
     * @param member_id 要删除的会员ID
     */
    void deleteMember(int member_id);

    /**
     * 批量删除系统中的会员信息。
     *
     * @param member_ids 要删除的会员ID列表
     */
    void deleteMembersByIds(List<Integer> member_ids);

    /**
     * 获取系统中所有的会员信息。
     *
     * @return 包含所有会员信息的列表
     */
    List<Member> getAllMembers();

    /**
     * 根据社团ID获取该社团的所有会员信息。
     *
     * @param club_id 社团ID
     * @return 包含该社团所有会员信息的列表
     */
    List<Member> getMembersByclub_id(int club_id);

    /**
     * 根据搜索关键词模糊查询系统中的会员信息。
     *
     * @param searchTerm 搜索关键词
     * @return 包含查询结果的会员列表
     */
    List<Member> searchMembersByTerm(String searchTerm);

    /**
     * 分页查询系统中的会员信息。
     *
     * @param page 页码
     * @param size 每页大小
     * @param searchTerm 搜索关键词
     * @param club_id 社团ID（可选）
     * @return 包含分页信息的会员列表
     */
    PageInfo<Member> getMembersPaginated(int page, int size, String searchTerm, Integer club_id);

    /**
     * 根据会员ID获取系统中相应的会员信息。
     *
     * @param member_id 要获取信息的会员ID
     * @return 包含会员信息的对象
     */
    Member getMemberById(int member_id);
}
