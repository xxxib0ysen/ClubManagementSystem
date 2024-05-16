package com.qst.clubmanagementsystem.dao;

import com.qst.clubmanagementsystem.entity.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/11
 */
@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO Members (club_id, member_name) VALUES (#{club_id}, #{member_name})")
    @Options(useGeneratedKeys = true, keyProperty = "member_id")
    void insertMember(Member member);

    @Update("UPDATE Members SET club_id = #{club_id}, member_name = #{member_name} WHERE member_id = #{member_id}")
    void updateMember(Member member);

    @Delete("DELETE FROM Members WHERE member_id = #{member_id}")
    void deleteMember(int member_id);

    @Delete("<script>DELETE FROM Members WHERE member_id IN <foreach item='member_id' collection='member_ids' open='(' separator=',' close=')'>#{member_id}</foreach></script>")
    void deleteMembers(@Param("member_ids") List<Integer> member_ids);

    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id WHERE m.member_name LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Member> searchMembers(@Param("searchTerm") String searchTerm);

    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id")
    List<Member> selectAllMembers();

    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id WHERE m.club_id = #{club_id}")
    List<Member> selectMembersByclub_id(@Param("club_id") int club_id);

    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id LIMIT #{offset}, #{limit}")
    List<Member> selectMembersPaginated(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT m.*, c.club_name FROM Members m JOIN Clubs c ON m.club_id = c.club_id WHERE m.member_id = #{member_id}")
    Member getMemberById(@Param("member_id") int memberId);
}