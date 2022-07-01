package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.MemberService;

@RestController
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping("/member/info")
    public String memberinfo(){
        return memberService.인증결과호출();
    }
}
