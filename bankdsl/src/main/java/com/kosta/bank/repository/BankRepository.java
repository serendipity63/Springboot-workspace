package com.kosta.bank.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.bank.entity.Account;
import com.kosta.bank.entity.Member;
import com.kosta.bank.entity.QAccount;
import com.kosta.bank.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BankRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	EntityManager entityManager;

	public Account findAccountById(String id) {
		QAccount account = QAccount.account;
		return jpaQueryFactory.select(account).from(account).where(account.id.eq(id)).fetchOne();
	}

	public List<Account> findAllAccount() {
		QAccount account = QAccount.account;
		return jpaQueryFactory.select(account).from(account).fetch();

	}

	@Transactional
	public void updateBalance(String id, Integer balance) {
		QAccount account = QAccount.account;
		jpaQueryFactory.update(account).set(account.balance, balance).where(account.id.eq(id)).execute();
		entityManager.flush();
		entityManager.clear();

	}

	public void insertAccount(Account acc) {
		accountRepository.save(acc);
	}

//insert가 불가능하다
	public Member selectMemberById(String id) {
		QMember member = QMember.member;
		return jpaQueryFactory.selectFrom(member).where(member.id.eq(id)).fetchOne();

	}

	public Member selectMemberByIdAndPassword(String id, String password) {
		QMember member = QMember.member;

		return jpaQueryFactory.selectFrom(member).where(member.id.eq(id).and(member.password.eq(password))).fetchOne();

	}

}
