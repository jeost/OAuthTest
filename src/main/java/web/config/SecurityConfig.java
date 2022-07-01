package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.service.IndexService;
import web.service.MemberService;

@Configuration // config 로 사용하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 시큐리티를 상속받아 내가 커스텀
    @Override
    protected void configure(HttpSecurity http) throws Exception { // HTTP 프로토콜 시큐리티 커스텀
        http.authorizeHttpRequests().antMatchers("/").permitAll() // 제일 위(/) 요청 들어오면 무조건 허용
                .and() //그리고
                .formLogin() // 폼 로그인은
                .loginPage("/member/login") // 로그인페이지를 지정
                .loginProcessingUrl("/member/logincontroller") // 로그인 처리할 곳을 지정
                .usernameParameter("mid") // 유저네임 파라미터명을 지정
                .passwordParameter("mpassword") // 비밀번호 파라미터명을 지정
                .failureUrl("/member/login/error") // 로그인 실패시 이동할 url 을 지정
                .and() // 그리고
                .logout() // 로그아웃은
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 여기로 왔을때 로그아웃 시행
                .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 주소
                .invalidateHttpSession(true) // 로그인세션 비우는거 true로
                .and() // 그리고
                .csrf().ignoringAntMatchers("/member/logincontroller") // 여기다 요청하는거 허용(기본값은 비허용)
                .and() //그리고
                .csrf().ignoringAntMatchers("/member/signupcontroller") // 여기다 요청하는것도 허용
                .and() // 그리고
                .oauth2Login() // OAUTH 로그인
                .userInfoEndpoint() // 유저 정보가 들어오는 위치
                .userService(memberService); // 멤버서비스로 정보 받기
    }

    @Autowired // 자동으로 메모리 할당
    IndexService indexService; // IndexService 를 이 클래스에서 사용하겠다

    @Autowired
    MemberService memberService;
    @Override // 메소드 기본값으로 안쓰고 내가 커스텀
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 비밀번호 맞는지 처리는
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder()); // BCrypt 인코더 사용해서 처리
    }

}
