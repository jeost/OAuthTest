package web.dto;

import lombok.*;
import web.domain.MemberEntity;
import web.domain.Role;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2Dto {

    private String mid;
    private String email;
    private String registrationId;
    private Map<String, Object> attributes;
    private String nameAttributeKey;

    public static OAuth2Dto sns확인(String registrationId,String nameAttributeKey, Map<String, Object> attributes){
        if(registrationId.equals("naver")){ // 네이버 로그인이면
            return naver(registrationId,attributes,nameAttributeKey); // 네이버 메소드로 넘긴 값을 리턴
        }else if(registrationId.equals("kakao")){ // 카카오 로그인이면
            return kakao(registrationId,attributes,nameAttributeKey); // 카카오 메소드로 넘긴 값을 리턴
        }
        return null;
    }
    public static OAuth2Dto naver(String registrationId, Map<String, Object> attributes, String nameAttributeKey){
        Map<String, Object> response= (Map<String, Object>) attributes.get(nameAttributeKey); // 넘긴 값을 받아서
        return OAuth2Dto.builder().mid((String)response.get("email")).build(); // 이메일을 리턴
    }
    public static OAuth2Dto kakao(String registrationId, Map<String, Object> attributes, String nameAttributeKey){
        Map<String, Object> kakao_accountMap= (Map<String, Object>) attributes.get(nameAttributeKey); // 넘긴 값을 받아서
        return OAuth2Dto.builder().mid((String)kakao_accountMap.get("email")).build(); // 이메일을 리턴
    }
    public MemberEntity toEntity(){ // MemberEntity 객체로 만드는 메소드
        return MemberEntity.builder().mid(this.mid).role(Role.OAuth2).build(); // 아이디랑 역할만 빌드
    }
}
