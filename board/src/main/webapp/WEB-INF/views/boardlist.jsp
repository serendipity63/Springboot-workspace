<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring 게시판</title>
<style type="text/css">
h3, h5 {
	text-align: center;
}

table {
	margin: auto;
	width: 800px
}

#tr_top {
	background: #ADD8E6;
	text-align: center;
}

#emptyArea {
	margin: auto;
	width: 800px;
	text-align: center;
}

#emptyArea a {
	display: inline-block;
	width: 20px;
	height: 20px;
	text-decoration: none;
}

#emptyArea .btn {
	background: lightgray;
}

#emptyArea .select {
	background: lightblue;
}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>

	function callBtn(num){
		var type=$("#type").val();
		var keyword=$("#keyword").val();
		if(type!='all' && keyword!=null && keyword.trim()!='' ){
			$('#page').val(num);
			$('#searchform').submit();
			return false;
		}
	}
	
</script>
</head>
<body>

	<h3>
		<a href="${contextPath }/main">메인</a> &nbsp;&nbsp; 
		글 목록 &nbsp;&nbsp; 
		<c:if test="${user ne Empty }">
			<!--로그인 안하면 글쓰기 안보이게  ne= not equal -->
			<a href="${contextPath }/boardwrite">글쓰기</a>
		</c:if>
	</h3>

	<form action="${contextPath }/boardsearch" method="post" id="searchform">
		<input type="hidden" name="page" id="page" value="1">
		<h5>
			<select name="type">
				<option value="all">선택</option>
				<option value="subject" ${type eq 'subject'? 'selected':''}>제목</option>
				<option value="writer" ${type eq 'writer'? 'selected':''}>작성자</option>
				<option value="content" ${type eq 'content'? 'selected':''}>내용</option>
			</select> <input type="text" name="keyword" id="keyword" value="${keyword }" />
			<input type="submit" value="검색" />
		</h5>
	</form>
	<table>
		<tr id="tr_top">
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수</th>
			<th>삭제</th>

			<c:forEach items="${boardList }" var="board">
				<tr>
					<td>${board.num }</td>
					<td><a href="${contextPath }/boarddetail/${board.num }/${pageInfo.curPage}">${board.subject}</a>
					</td>
					<td>${board.writer }</td>
					<td>${board.writedate }</td>
					<td>${board.viewcount }</td>
					<!--작성자만 삭제할 수 있게 하게 -->
					<td><c:if test="${user.id == board.writer }">
							<a
								href="${contextPath }/boarddelete/${board.num }/${pageInfo.curPage}"
							>삭제</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tr>
	</table>
	<div id="emptyArea">
		<c:choose>
			<c:when test="${pageInfo.curPage>1 }">
				<a href="${contextPath }/boardlist/${pageInfo.curPage-1 }" onclick="return callBtn(${pageInfo.curPage-1 });">&lt;</a>
			</c:when>
			<c:otherwise>
	 		&lt;
	 	</c:otherwise>
		</c:choose>
		&nbsp;&nbsp;

		<c:forEach begin="${pageInfo.startPage }" end="${pageInfo.endPage }"
			var="i"
		>
			<c:choose>
				<c:when test="${pageInfo.curPage==i }">
					<a href="${contextPath }/boardlist/${i}" class="select"
						onclick="return callBtn(${i});"
					>${i}</a> &nbsp;	
			</c:when>
				<c:otherwise>
					<a href="${contextPath }/boardlist/${i}" class="btn"
						onclick="return callBtn(${i});"
					>${i}</a> &nbsp;	
			</c:otherwise>
			</c:choose>

		</c:forEach>

		<c:choose>
			<c:when test="${pageInfo.curPage<pageInfo.allPage}">
				<a href="${contextPath }/boardlist/${pageInfo.curPage+1 }" onclick="return callBtn(${pageInfo.curPage+1});">&gt;</a>
			</c:when>
			<c:otherwise>
				&gt;
			</c:otherwise>
		</c:choose>
		&nbsp;&nbsp;
	</div>

</body>
</html>