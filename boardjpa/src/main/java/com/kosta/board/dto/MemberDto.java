package com.kosta.board.dto;

import com.kosta.board.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
	private String id;
	private String name;
	private String password;
	private String email;
	private String address;

	public Member toEntity() { // static으로 안한이유
		return Member.builder().id(id).name(name).password(password).email(email).address(address).build();
	}

}