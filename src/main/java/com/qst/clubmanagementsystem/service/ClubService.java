package com.qst.clubmanagementsystem.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;

import java.util.List;

/**
 * 定义了对社团信息的操作。
 *
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */
public interface ClubService {

    /**
     * 向系统中添加新的社团。
     *
     * @param club 要添加的社团对象
     */
    void addClub(Club club);

    /**
     * 更新系统中的社团信息。
     *
     * @param club 包含更新信息的社团对象
     */
    void updateClub(Club club);

    /**
     * 根据社团ID删除系统中的社团信息。
     *
     * @param club_id 要删除的社团ID
     */
    void deleteClub(int club_id);

    /**
     * 批量删除系统中的社团信息。
     *
     * @param club_ids 要删除的社团ID列表
     */
    void deleteClubsByIds(List<Integer> club_ids);

    /**
     * 获取系统中所有的社团信息。
     *
     * @return 包含所有社团信息的列表
     */
    List<Club> getAllClubs();

    /**
     * 更新指定社团的图片链接。
     *
     * @param club_id 要更新图片链接的社团ID
     * @param image_url 新的图片链接
     */
    void updateClubImage(int club_id, String image_url);

    /**
     * 获取每个社团的成员数量统计信息。
     *
     * @return 包含社团成员数量统计信息的列表
     */
    List<ClubMemberCount> getClubMemberCounts();

    /**
     * 分页获取社团信息。
     *
     * @param pageNumber 页码
     * @param pageSize 每页大小
     * @param club_name 社团名称
     * @return 包含分页信息的社团列表
     */
    PageInfo<Club> getClubsPaginated(int pageNumber, int pageSize, String club_name);

    /**
     * 根据社团名称模糊搜索社团信息。
     *
     * @param club_name 社团名称的一部分
     * @return 包含查询结果的分页对象
     */
    Page<Club> searchClubsByName(String club_name);

    /**
     * 根据社团ID获取社团信息。
     *
     * @param club_id 要获取信息的社团ID
     * @return 包含社团信息的对象
     */
    Club getClubById(int club_id);
}
