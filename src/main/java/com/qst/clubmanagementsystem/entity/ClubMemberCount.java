package com.qst.clubmanagementsystem.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/7
 */

@Setter
@Getter
public class ClubMemberCount {
    private String club_name;
    private int memberCount;

    public ClubMemberCount(String club_name, int memberCount) {
        this.club_name = club_name;
        this.memberCount = memberCount;
    }

}
