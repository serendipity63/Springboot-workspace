package com.kosta.board.dto;

import java.sql.Date;

import com.kosta.board.entity.Board;
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
public class BoardDto {
	private Integer num;
	private String subject;
	private String content;
	private Date writedate;
	private String fileurl;
	private String writer;
	private Integer viewcount;
	private Integer likecount;
	private String writername;

	public Board toEntity() {
		return Board.builder().num(num).subject(subject).content(content).fileurl(fileurl)
				.member(Member.builder().id(writer).build()).viewcount(viewcount).likecount(likecount)
				.writedate(writedate).build(); // entity의 속성(파라미터 안에 있는건 dto의 변수값)

		// static이 아닌 인스턴스 매소드 자기 변수를 이용해서 dto를 만든다
	}

}