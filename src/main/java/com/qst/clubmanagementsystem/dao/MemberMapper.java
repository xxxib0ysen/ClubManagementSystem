package com.qst.clubmanagementsystem.dao;

import com.qst.clubmanagementsystem.entity.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用于与数据库交互，执行对会员信息的增删改查操作。
 *
 * @Author xxxib0ysen
 * @Date 2024/5/11
 */
@Mapper
public interface MemberMapper {

    /**
     * 向数据库中插入新的会员信息。
     *
     * @param member 要插入的会员对象
     */
    @Insert("INSERT INTO Members (club_id, member_name) VALUES (#{club_id}, #{member_name})")
    @Options(useGeneratedKeys = true, keyProperty = "member_id")
    void insertMember(Member member);

    /**
     * 更新数据库中的会员信息。
     *
     * @param member 包含更新信息的会员对象
     */
    @Update("UPDATE Members SET club_id = #{club_id}, member_name = #{member_name} WHERE member_id = #{member_id}")
    void updateMember(Member member);

    /**
     * 根据会员ID从数据库中删除相应的会员信息。
     *
     * @param member_id 要删除的会员ID
     */
    @Delete("DELETE FROM Members WHERE member_id = #{member_id}")
    void deleteMember(int member_id);

    /**
     * 批量删除数据库中的会员信息。
     *
     * @param member_ids 要删除的会员ID列表
     */
    @Delete("<script>DELETE FROM Members WHERE member_id IN <foreach item='member_id' collection='member_ids' open='(' separator=',' close=')'>#{member_id}</foreach></script>")
    void deleteMembers(@Param("member_ids") List<Integer> member_ids);

    /**
     * 根据搜索关键词模糊查询数据库中的会员信息。
     *
     * @param searchTerm 搜索关键词
     * @return 包含查询结果的会员列表
     */
    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id WHERE m.member_name LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Member> searchMembers(@Param("searchTerm") String searchTerm);

    /**
     * 查询数据库中的所有会员信息。
     *
     * @return 包含查询结果的会员列表
     */
    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id")
    List<Member> selectAllMembers();

    /**
     * 根据社团ID查询数据库中该社团的所有会员信息。
     *
     * @param club_id 社团ID
     * @return 包含查询结果的会员列表
     */
    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id WHERE m.club_id = #{club_id}")
    List<Member> selectMembersByclub_id(@Param("club_id") int club_id);

    /**
     * 分页查询数据库中的会员信息。
     *
     * @param offset 分页偏移量
     * @param limit 分页大小
     * @return 包含查询结果的会员列表
     */
    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id LIMIT #{offset}, #{limit}")
    List<Member> selectMembersPaginated(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据会员ID从数据库中获取相应的会员信息。
     *
     * @param member_id 要获取信息的会员ID
     * @return 包含会员信息的对象
     */
    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id WHERE m.member_id = #{member_id}")
    Member getMemberById(@Param("member_id") int member_id);
}
