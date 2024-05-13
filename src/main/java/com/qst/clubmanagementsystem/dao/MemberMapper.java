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
    void deleteMembers(List<Integer> member_ids);

    @Select("SELECT * FROM Members WHERE member_name LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Member> searchMembers(String searchTerm);

    @Select("SELECT * FROM Members")
    List<Member> selectAllMembers();

    @Select("SELECT * FROM Members WHERE club_id = #{club_id}")
    List<Member> selectMembersByclub_id(int club_id);

    @Select("SELECT * FROM Members LIMIT #{offset}, #{limit}")
    List<Member> selectMembersPaginated(int offset, int limit);
}