package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.domain.MemberEntity;
import web.domain.Repository;
import web.domain.Role;
import web.dto.LoginDto;
import web.dto.MemberDto;

import java.util.ArrayList;
import java.util.List;

@Service // 여기를 Service 로 쓰겠다
public class IndexService implements UserDetailsService { // UserDetailsService 를 가져와서 구현(로그인을 커스텀)
    @Autowired // 자동 메모리 할당
    Repository repository; // Repository 를 쓰겠다
    public void signup(MemberDto memberDto){ // 전달받은 dto 를 받아서
        repository.save(memberDto.toEntity()); // 엔티티화해서 DB에 저장
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // loadUserByUsername 구현
        MemberEntity memberEntity=repository.findByMid(username).get(); // mid 로 유저를 찾아서 엔티티에 저장

        List<GrantedAuthority> authorities=new ArrayList<>(); // 빈 리스트 선언
        authorities.add(new SimpleGrantedAuthority(Role.Member.getKey())); // 멤버 역할을 선언해서 리스트에 추가

        LoginDto loginDto=new LoginDto(memberEntity.getMid(), memberEntity.getMpassword(), authorities); // 역할을 담아서 LoginDto 저장
        return loginDto; // loginDto 리턴
    }
}
