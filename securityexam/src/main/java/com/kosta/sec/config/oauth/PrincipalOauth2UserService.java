package com.kosta.sec.config.oauth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.kosta.sec.config.auth.PrincipalDetails;
import com.kosta.sec.entity.User;
import com.kosta.sec.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	// userRequest는 code를 받아서 accessToken을 응답받은 객체
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	private final String oauthPassword = "펩과르디올라";

	// userRequest는 code를 받아서 access Token을 응답받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//		System.out.println("accessToken:" + userRequest.getAccessToken().getTokenValue());
//		System.out.println("ClientRegistration:" + userRequest.getClientRegistration());
//		System.out.println("oAuth2User" + super.loadUser(userRequest));
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User);
		System.out.println(oAuth2User.getAttributes());
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttribute("response"));
			// response값이 실제 데이터
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			System.out.println("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
//			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttribute("response"));

		} else {
			System.out.println("네이버와 카카오만 지원합니다");
		}

		Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(),
				oAuth2UserInfo.getProviderId());
		User user = null;
		if (userOptional.isPresent()) { // 이미 가입되어 있으면 update
			user = userOptional.get();
			user.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
		} else { // 가입되어 있지 않으면 insert
			user = User.builder().username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail()).roles("ROLE_USER").provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
//					.password(bCryptPasswordEncoder.encode(oauthPassword))
					.build();
			userRepository.save(user);
		}
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}
