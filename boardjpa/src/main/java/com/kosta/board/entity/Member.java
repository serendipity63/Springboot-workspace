package com.kosta.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	// 필요할때 가져오는거라서 lazy
	private List<Board> boardList = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("[%s,%s,%s,%s,%s]", id, name, password, email, address);
	}

}
