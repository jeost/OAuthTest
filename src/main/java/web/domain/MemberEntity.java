package web.domain;

import lombok.*;

import javax.persistence.*;

@Entity // 여기를 엔티티로 쓰겠다
@Table(name = "membertest") // 테이블명은 membertest 다
@AllArgsConstructor // 풀 생성자
@NoArgsConstructor // 빈 생성자
@Getter // get 머시기 만들어줌
@Setter // set 머시기 만들어줌
@ToString // ToString 알아서 생성
@Builder // Builder(set 머시기보다 편한거) 생성
public class MemberEntity {
    @Id // PK 값으로 이걸 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB 에서 AUTO_INCREMENT 같은거
    private int mno; // 자동 생성되는 회원번호

    private String mid; // 아이디

    private String mpassword; // 비밀번호

    private Role role; // 역할(권한)
}
