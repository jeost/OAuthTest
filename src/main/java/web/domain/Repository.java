package web.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Entity 옆에 놔두면 @Repository 생략 가능
public interface Repository extends JpaRepository<MemberEntity,Integer> { // JPA 리포지토리 상속받고 엔티티 클래스랑 ID의 자료형 넣으면 알아서 해줌

    Optional<MemberEntity> findByMid(String mid); // mid 로 찾아주는 메소드 선언
}
