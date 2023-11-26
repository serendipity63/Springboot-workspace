package com.kosta.sec.config.oauth;

public interface OAuth2UserInfo {
	String getProviderId();

	String getProvider(); // naver kakao

	String getEmail();

	String getName();

}
