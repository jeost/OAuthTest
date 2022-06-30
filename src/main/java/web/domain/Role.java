package web.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final로 선언된 애들로 생성자 만들어줌
@Getter // get 머시기 추가해줌
public enum Role {
    Member("ROLE_MEMBER","회원"), // 멤버 역할
    ADMIN("ROLE_ADMIN","어드민"); // 어드민 역할
    private final String key;
    private final String keyword;
}
