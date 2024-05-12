package com.qst.clubmanagementsystem.controller;

import com.qst.clubmanagementsystem.entity.Menu;
import com.qst.clubmanagementsystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/8
 */

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    // 获取所有菜单项
    @GetMapping("/")
    public List<Menu> getMenus() {
        return menuService.getAllMenus();
    }
}
