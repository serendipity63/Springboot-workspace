package com.kosta.sec.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kosta.sec.entity.User;

import lombok.Data;

//security가 /loginProc 주소를 낚아채서 로그인을 진행시킨다.
//로그인 진행이 완료가 되면 security session을 만들어준다
//security session에 들어가는 타입은 Authentication 타입의 객체여야 한다
//Authentication 안에 User 정보를 넣어야 한다
//그 User 오브젝트 타입은 UserDetails 타입이여야 한다.
@Data
public class PrincipalDetails implements UserDetails {
	private User user;
	private Map<String, Object> attributes;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRoles();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() { // 정지 아이디 (활중 몇개월 ㅇㅇ)
		return true;
	}

}
