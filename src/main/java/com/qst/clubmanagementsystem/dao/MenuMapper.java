package com.qst.clubmanagementsystem.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import  com.qst.clubmanagementsystem.entity.Menu;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/8
 */
@Mapper
public interface MenuMapper {
    @Select("SELECT * FROM Menus ORDER BY parent_id, id")
    List<Menu> getAllMenus();
}
