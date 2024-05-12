package com.qst.clubmanagementsystem.dao;

import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */
@Mapper
public interface ClubMapper {

    // 插入一个新的社团，并自动回填主键
    @Insert("INSERT INTO Clubs (club_name, description, image_url) VALUES (#{clubName}, #{description}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "clubId")
    void insertClub(Club club);


    // 更新指定ID的社团信息
    @Update("UPDATE Clubs SET club_name = #{clubName}, description = #{description}, image_url = #{imageUrl} WHERE club_id = #{clubId}")
    void updateClub(Club club);


    // 根据ID删除一个社团
    @Delete("DELETE FROM Clubs WHERE club_id = #{clubId}")
    void deleteClub(int clubId);


    // 批量删除社团
    @Delete("<script>" +
            "DELETE FROM Clubs WHERE club_id IN " +
            "<foreach item='clubId' collection='clubIds' open='(' separator=',' close=')'>" +
            "#{clubId}" +
            "</foreach>" +
            "</script>")
    void deleteClubs(List<Integer> clubIds);


    // 根据名称进行模糊查询
    @Select("SELECT * FROM Clubs WHERE club_name LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Club> searchClubs(String searchTerm);


    // 查询所有社团
    @Select("SELECT * FROM Clubs")
    List<Club> selectAllClubs();


    // 分页查询社团信息
    @Select("SELECT * FROM Clubs LIMIT #{offset}, #{limit}")
    List<Club> selectClubsPaginated(int offset, int limit);


    // 更新社团的图片URL
    @Update("UPDATE Clubs SET image_url = #{imageUrl} WHERE club_id = #{clubId}")
    void updateClubImage(int clubId, String imageUrl);


    @Select("SELECT c.club_name, COUNT(m.member_id) AS member_count " +
            "FROM Clubs c LEFT JOIN Members m ON c.club_id = m.club_id " +
            "GROUP BY c.club_id, c.club_name " +
            "ORDER BY member_count DESC")
    List<ClubMemberCount> getClubMemberCounts();

}
