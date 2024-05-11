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

    // 插入一个新的会员，并自动回填主键
    @Insert("INSERT INTO Members (club_id, member_name, email) VALUES (#{clubId}, #{memberName}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    int insertMember(Member member);

    // 更新指定ID的会员信息
    @Update("UPDATE Members SET club_id = #{clubId}, member_name = #{memberName}, email = #{email} WHERE member_id = #{memberId}")
    void updateMember(Member member);

    // 根据ID删除一个会员
    @Delete("DELETE FROM Members WHERE member_id = #{memberId}")
    void deleteMember(int memberId);

    // 批量删除会员
    @Delete("<script>" +
            "DELETE FROM Members WHERE member_id IN " +
            "<foreach item='memberId' collection='list' open='(' separator=',' close=')'>" +
            "#{memberId}" +
            "</foreach>" +
            "</script>")
    void deleteMembers(List<Integer> memberIds);

    // 根据名称进行模糊查询
    @Select("SELECT * FROM Members WHERE member_name LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Member> searchMembers(String searchTerm);

    // 查询所有会员
    @Select("SELECT * FROM Members")
    List<Member> selectAllMembers();

    // 分页查询会员信息
    @Select("SELECT * FROM Members LIMIT #{offset}, #{limit}")
    List<Member> selectMembersPaginated(int offset, int limit);
}