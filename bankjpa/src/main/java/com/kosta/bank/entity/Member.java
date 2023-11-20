package com.kosta.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.kosta.bank.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Member {
	@Id
	private String id;

//	@Column (name="")
	@Column
	private String name;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String address;

	// 모델 매퍼를 안쓰고 만들어서 썼다
	// dto가 생성되었을때 entity를 만들어준다

	public static Member toEntity(MemberDto memberDto) {
		return Member.builder().id(memberDto.getId()).name(memberDto.getName()).password(memberDto.getPassword())
				.email(memberDto.getEmail()).address(memberDto.getAddress()).build();
	}

}
