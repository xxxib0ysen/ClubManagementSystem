package com.qst.clubmanagementsystem.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qst.clubmanagementsystem.entity.Club;
import com.qst.clubmanagementsystem.entity.ClubMemberCount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用于与数据库交互，执行对社团信息的增删改查操作。
 *
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */
@Mapper
public interface ClubMapper {

    /**
     * 向数据库中插入新的社团信息。
     *
     * @param club 要插入的社团对象
     */
    @Insert("INSERT INTO Clubs (club_name, description, image_url) VALUES (#{club_name}, #{description}, #{image_url})")
    @Options(useGeneratedKeys = true, keyProperty = "club_id")
    void insertClub(Club club);

    /**
     * 更新数据库中的社团信息。
     *
     * @param club 包含更新信息的社团对象
     */
    @Update("UPDATE Clubs SET club_name = #{club_name}, description = #{description}, image_url = #{image_url} WHERE club_id = #{club_id}")
    void updateClub(Club club);

    /**
     * 根据社团ID从数据库中删除相应的社团信息。
     *
     * @param club_id 要删除的社团ID
     */
    @Delete("DELETE FROM Clubs WHERE club_id = #{club_id}")
    void deleteClub(int club_id);

    /**
     * 批量删除数据库中的社团信息。
     *
     * @param club_ids 要删除的社团ID列表
     */
    @Delete("<script>DELETE FROM Clubs WHERE club_id IN <foreach item='club_id' collection='club_ids' open='(' separator=',' close=')'>#{club_id}</foreach></script>")
    void deleteClubs(@Param("club_ids") List<Integer> club_ids);

    /**
     * 根据社团名称模糊查询数据库中的社团信息并分页返回结果。
     *
     * @param club_name 社团名称的一部分
     * @return 包含查询结果的分页对象
     */
    @Select("SELECT * FROM Clubs WHERE club_name LIKE CONCAT('%', #{club_name}, '%')")
    Page<Club> searchClubsByName(@Param("club_name") String club_name);

    /**
     * 查询数据库中的所有社团信息并分页返回结果。
     *
     * @return 包含查询结果的分页对象
     */
    @Select("SELECT * FROM Clubs")
    Page<Club> selectAllClubs();

    /**
     * 更新数据库中指定社团的图片链接信息。
     *
     * @param club_id 社团ID
     * @param image_url 新的图片链接
     */
    @Update("UPDATE Clubs SET image_url = #{image_url} WHERE club_id = #{club_id}")
    void updateClubImage(@Param("club_id") int club_id, @Param("image_url") String image_url);

    /**
     * 查询每个社团的成员数量，并按成员数量降序排序。
     *
     * @return 包含社团成员数量信息的列表
     */
    @Select("SELECT c.club_name, COUNT(m.member_id) AS member_count FROM Clubs c LEFT JOIN Members m ON c.club_id = m.club_id GROUP BY c.club_id, c.club_name ORDER BY member_count DESC")
    List<ClubMemberCount> getClubMemberCounts();

    /**
     * 根据社团ID从数据库中获取相应的社团信息。
     *
     * @param club_id 要获取信息的社团ID
     * @return 包含社团信息的对象
     */
    @Select("SELECT * FROM Clubs WHERE club_id = #{club_id}")
    Club getClubById(@Param("club_id") int club_id);
}
