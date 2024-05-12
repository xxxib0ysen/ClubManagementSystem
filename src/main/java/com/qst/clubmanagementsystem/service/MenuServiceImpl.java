package com.qst.clubmanagementsystem.service;

import com.qst.clubmanagementsystem.dao.MenuMapper;
import com.qst.clubmanagementsystem.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/8
 */

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}