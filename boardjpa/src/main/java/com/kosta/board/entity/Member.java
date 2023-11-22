package com.kosta.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kosta.board.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	@Id
	private String id;

	@Column
	private String name;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String address;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Board> boardList = new ArrayList<>(); // 필요할때 가져오는거라서 lazy

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Boardlike> boardlike = new ArrayList<>(); // 필요할때 가져오는거라서 lazy

	@Override
	public String toString() {
		return String.format("[%s,%s,%s,%s,%s]", id, name, password, email, address);
	}

	public MemberDto toDto() {
		return MemberDto.builder().id(id).name(name).password(password).email(email).address(address).build();

	}
}
