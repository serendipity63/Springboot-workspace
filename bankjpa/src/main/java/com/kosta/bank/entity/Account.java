package com.kosta.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//entity는 테이블이다
public class Account {
	@Id
	private String id;

	@Column
	private String name;
	@Column
	private Integer balance;
	@Column
	private String type;
	@Column
	private String grade;

	public void deposit(Integer money) {
		if (money > 0) {
			balance += money;
		}
	}

	public void withdraw(Integer money) throws Exception {
		if (balance > money) {
			balance -= money;
		} else {
			throw new Exception("잔액이 부족합니다");
		}
	}
}
