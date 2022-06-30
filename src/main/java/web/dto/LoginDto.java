package web.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter // get 머시기 생성
public class LoginDto implements UserDetails { // UserDetails 를 여기서 구현
    private String mid; // 회원 아이디
    private String mpassword; // 회원 비밀번호
    private Set<GrantedAuthority> authorities; // 권한

    // GrantedAuthority 에서 상속받아야 함(API가 그러함)
    public LoginDto(String mid, String mpassword, Collection<? extends GrantedAuthority> authorities){
        this.mid=mid;
        this.mpassword=mpassword;
        // unmodifiableSet 으로 감싸면 주어진 집합에 대해 수정할 수 없게 해줌
        this.authorities= Collections.unmodifiableSet(new LinkedHashSet<>(authorities));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.mpassword;
    }

    @Override
    public String getUsername() {
        return this.mid;
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠김
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 계정 비밀번호 변경 후 오래되었는지
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화
        return true;
    }
}
