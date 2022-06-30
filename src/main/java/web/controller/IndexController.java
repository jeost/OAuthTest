package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.dto.MemberDto;
import web.service.IndexService;

@Controller // 여기를 Controller 로 사용하겠다
public class IndexController {
    @Autowired // 자동 메모리 할당
    private IndexService indexService; // IndexService 를 사용하겠다

    @GetMapping("/") // (/) 로 들어왔을때
    public String main(){ // String 값(html 파일명) 반환
        return "main"; // main 반환
    }

    @GetMapping("/member/login") // 여기로 들어오면
    public String login(){ // String 값(html 파일명) 반환
        return "login"; // login 반환
    }
    @GetMapping("/member/signup") // 여기로 들어오면
    public String signup(){ // String 값(html 파일명) 반환
        return "signup"; // signup 반환
    }
    @PostMapping("/member/signupcontroller") // 여기로 들어오면
    public String signupcontroller(MemberDto dto){ // String 값(html 파일명) 반환
        indexService.signup(dto); // 하기전에 받은 파라미터값을 dto(자동주입)값으로 signup 메소드에 넘기고
        return "redirect:/";
    }
}
