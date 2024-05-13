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
    @PutMapping("/{member_id}")
    public void updateMember(@PathVariable int member_id, @RequestBody Member member) {
        member.setMember_id(member_id);
        memberService.updateMember(member);
    }

    // 删除指定ID的会员
    @DeleteMapping("/{member_id}")
    public void deleteMember(@PathVariable int member_id) {
        memberService.deleteMember(member_id);
    }

    // 批量删除会员
    @DeleteMapping("/batch-delete")
    public void deleteMembers(@RequestBody List<Integer> member_ids) {
        memberService.deleteMembersByIds(member_ids);
    }

    // 获取所有会员列表
    @GetMapping("/")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    // 根据社团ID获取对应的会员列表
    @GetMapping("/by-club/{club_id}")
    public List<Member> getMembersByclub_id(@PathVariable int club_id) {
        return memberService.getMembersByclub_id(club_id);
    }

    // 模糊查询会员
    @GetMapping("/search")
    public List<Member> searchMembers(@RequestParam String searchTerm) {
        return memberService.searchMembersByTerm(searchTerm);
    }

    // 分页查询会员
    @GetMapping("/page")
    public List<Member> getMembersPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return memberService.getMembersPaginated(page, size);
    }
}