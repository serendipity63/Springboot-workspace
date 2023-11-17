package com.kosta.bank.dto;

import com.kosta.bank.entity.Member;

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

	public static MemberDto toDto(Member member) { // Entity -> Dto
		return MemberDto.builder().id(member.getId()).name(member.getName()).password(member.getPassword())
				.email(member.getEmail()).address(member.getAddress()).build();

	}
}
