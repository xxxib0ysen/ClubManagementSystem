package com.qst.clubmanagementsystem.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    @Insert("INSERT INTO Clubs (club_name, description, image_url) VALUES (#{club_name}, #{description}, #{image_url})")
    @Options(useGeneratedKeys = true, keyProperty = "club_id")
    void insertClub(Club club);

    @Update("UPDATE Clubs SET club_name = #{club_name}, description = #{description}, image_url = #{image_url} WHERE club_id = #{club_id}")
    void updateClub(Club club);

    @Delete("DELETE FROM Clubs WHERE club_id = #{club_id}")
    void deleteClub(int club_id);

    @Delete("<script>DELETE FROM Clubs WHERE club_id IN <foreach item='club_id' collection='club_ids' open='(' separator=',' close=')'>#{club_id}</foreach></script>")
    void deleteClubs(@Param("club_ids") List<Integer> club_ids);

    @Select("SELECT * FROM Clubs WHERE club_name LIKE CONCAT('%', #{club_name}, '%')")
    Page<Club> searchClubsByName(@Param("club_name") String club_name);

    @Select("SELECT * FROM Clubs")
    Page<Club> selectAllClubs();


    @Update("UPDATE Clubs SET image_url = #{image_url} WHERE club_id = #{club_id}")
    void updateClubImage(@Param("club_id") int club_id, @Param("image_url") String image_url);

    @Select("SELECT c.club_name, COUNT(m.member_id) AS member_count FROM Clubs c LEFT JOIN Members m ON c.club_id = m.club_id GROUP BY c.club_id, c.club_name ORDER BY member_count DESC")
    List<ClubMemberCount> getClubMemberCounts();

    @Select("SELECT * FROM Clubs WHERE club_id = #{club_id}")
    Club getClubById(@Param("club_id") int club_id);
}
