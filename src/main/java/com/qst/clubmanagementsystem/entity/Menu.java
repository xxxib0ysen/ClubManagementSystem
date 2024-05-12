package com.qst.clubmanagementsystem.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/8
 */

@Setter
@Getter
public class Menu {
    private int id;
    private Integer parentId;
    private String name;
    private String url;
    private String icon;

}