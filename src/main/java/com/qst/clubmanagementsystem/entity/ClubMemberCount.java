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
    private String clubName;
    private int memberCount;

    public ClubMemberCount(String clubName, int memberCount) {
        this.clubName = clubName;
        this.memberCount = memberCount;
    }

}
