package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import web.domain.MemberEntity;
import web.domain.Repository;
import web.dto.OAuth2Dto;

import java.util.*;

@Service
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired // 자동 빈 생성
    private Repository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null; // 지금 안 써서 대충 null 리턴
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { // 인터페이스 구현

        OAuth2UserService oAuth2UserService=new DefaultOAuth2UserService(); // OAuth2유저 서비스 메모리 할당
        OAuth2User oAuth2User=oAuth2UserService.loadUser(userRequest); // 인수로 받은 요청을 OAuth2User 객체에 담기

        Map<String, Object> userAttributes=oAuth2User.getAttributes(); // 위에서 담은 객체에서 Attributes 만 빼내서 Map 에 담기

        String registrationid=userRequest.getClientRegistration().getRegistrationId(); // 어떤 sns 로 로그인했는지 값 저장

        String nameattributekey=userRequest.getClientRegistration().getProviderDetails() // Provider 내의 정보 호출
                .getUserInfoEndpoint() // 유저 정보 엔드포인트에서
                .getUserNameAttributeName(); // 유저 이름(여기서는 이메일) 빼내서 저장

        OAuth2Dto oAuth2Dto=OAuth2Dto.sns확인(registrationid,nameattributekey,oAuth2User.getAttributes()); // sns확인 메소드에 저장한 값들 전달 후 리턴된 값을 OAuth2Dto 에 저장

        Optional<MemberEntity> optional=repository.findByMid(oAuth2Dto.getMid()); // Dto 에서 mid만 빼내서 저장소에서 검색 후
        if(!optional.isPresent()) { // 기존에 없던 사람일때만
            repository.save(oAuth2Dto.toEntity());    // 저장한다

//        else{
//            optional.get().setMid(oAuth2Dto.getMid());
//        }

        }
        // OAuth2 사용자라는 Role 을 부여한 채 나머지 값들과 함께 리턴
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("OAuth2 사용자")), userAttributes, nameattributekey);
    }
    public String 인증결과호출(){ // 로그인 후 인증결과를 리턴하는 메소드
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication(); // 어쩌고Holder 에서 인증결과를 호출해 객체에 저장

        Object principal=authentication.getPrincipal(); // 거기서 Principal 을 또 빼내서 저장

        String mid=null; // 초기값 null로 지정
        if(principal!=null){
            //로그인 된 상태일 경우
            if(principal instanceof UserDetails){ // 일반 유저라면
                mid=((UserDetails) principal).getUsername(); // 유저 이름을 그냥 빼낸다
            }else if(principal instanceof OAuth2User){ // OAuth2 를 사용한 유저라면
                Map<String,Object> attributes=((OAuth2User)principal).getAttributes(); // 형변환 후 Attributes 에서 빼내고
                if(attributes.get("response")!=null){ // 네이버라면
                    Map<String,Object> map= (Map<String, Object>) attributes.get("response"); // response 내부만 빼내서
                    mid=(String)map.get("email"); // 거기서 이메일값을 mid 에 저장
                }else if(attributes.get("kakao_account")!=null){ // 카카오라면
                    Map<String,Object> map= (Map<String, Object>) attributes.get("kakao_account"); // kakao_account 내부만 빼내서
                    mid=(String)map.get("email"); // 거기서 이메일값을 mid 에 저장
                }else{ //둘다 아니라면 (아직 미구현)

                }
            }
        }else{
            //로그인 안 된 상태
        }
        return mid; // 위에서 담은 값을 리턴
    }
}
