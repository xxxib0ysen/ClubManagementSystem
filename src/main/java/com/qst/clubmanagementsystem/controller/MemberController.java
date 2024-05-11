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

    @PostMapping("/")
    public void addMember(@RequestBody Member member) {
        memberService.addMember(member);
    }

    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable int memberId, @RequestBody Member member) {
        member.setMemberId(memberId);
        memberService.updateMember(member);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable int memberId) {
        memberService.deleteMember(memberId);
    }

    @GetMapping("/")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/by-club/{clubId}")
    public List<Member> getMembersByClubId(@PathVariable int clubId) {
        return memberService.getMembersByClubId(clubId);
    }
}
