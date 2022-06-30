package web.dto;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.domain.MemberEntity;
import web.domain.Role;

@AllArgsConstructor // 모든 필드 넣은 생성자
@NoArgsConstructor // 빈 생성자
@Getter // getter 생성
@Setter // setter 생성
@ToString // ToString 생성
@Builder // Builder 생성
public class MemberDto {
    private int mno; // 회원번호
    private String mid; // 회원 아이디
    private String mpassword; // 회원 비밀번호

    public MemberEntity toEntity(){ // DTO -> Entity
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(); // BCrypt 인코더 가져와서
        return MemberEntity.builder().mid(this.mid).mpassword(encoder.encode(this.mpassword)).role(Role.Member).build();
        // 인코딩한 패스워드와 나머지 값들을 엔티티화
    }
}
