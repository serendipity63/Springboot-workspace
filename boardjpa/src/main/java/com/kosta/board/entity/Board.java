package com.kosta.board.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.kosta.board.dto.BoardDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//entity는 테이블

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EnableJpaAuditing
@DynamicInsert
//인서트될때만 한다 column default랑 페어
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;

	@Column
	private String subject;
	@Column
	private String content;
	@Column
	private String fileurl;
	@Column
	@ColumnDefault("0")
	private Integer viewcount;
	@Column
	@ColumnDefault("0")
	private Integer likecount;
	@Column
	@CreationTimestamp
	private Date writedate;

	@ManyToOne(fetch = FetchType.EAGER)
	// 바로 바로 가져와서 eager
	@JoinColumn(name = "writer") // 관계를 맺는다 //writer는 member table과 조인된거다
	private Member member; // writer는 멤버타입으로 가지고 있는다

	@Override
	public String toString() {
		return String.format("[%d,%s,%s,%s,%s]", num, subject, content, fileurl, member.getId());
	}

	public BoardDto toDto() {
		return BoardDto.builder().num(num).subject(subject).content(content).fileurl(fileurl).writer(member.getId())
				.writername(member.getName()).viewcount(viewcount).likecount(likecount).writedate(writedate).build();
	}

}
