package com.qst.clubmanagementsystem.controller;

import com.qst.clubmanagementsystem.entity.Member;
import com.qst.clubmanagementsystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author xxxib0ysen
 * @Date 2024/5/6
 */

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 添加会员
    @PostMapping("/")
    public void addMember(@RequestBody Member member) {
        memberService.addMember(member);
    }

    // 更新会员信息
    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable int memberId, @RequestBody Member member) {
        member.setMemberId(memberId);
        memberService.updateMember(member);
    }

    // 删除会员
    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable int memberId) {
        memberService.deleteMember(memberId);
    }

    // 获取所有会员
    @GetMapping("/")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    // 根据社团ID获取会员
    @GetMapping("/by-club/{clubId}")
    public List<Member> getMembersByClubId(@PathVariable int clubId) {
        return memberService.getMembersByClubId(clubId);
    }
}
