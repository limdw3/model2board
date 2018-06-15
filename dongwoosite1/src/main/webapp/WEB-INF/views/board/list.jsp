<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
	<%@ include file="../include/header.jsp"%>



<div class="box">
		<div class="box-header with-border">
			<c:if test="${msg == null}">
				<h3 class="box-title">${admin.boardname} 게시판</h3>
			</c:if>
			<!-- 이거는 내가 뭐했는지(삽입,삭제,수정) 알려줄려공... -->
			<c:if test="${msg != null}">
				<h3 class="box-title">${msg}</h3>
			</c:if>
		</div>
		<div class="box-body">
			<table class="table table-bordered table-hover">
				<tr>
					<th width="11%">글번호</th>
					<th width="46%">제목</th>
					<th width="16%">작성자</th>
					<th width="16%">작성일</th>
					<th width="11%">조회수</th>
				</tr>
				<c:forEach var="vo" items="${list}">
				<c:if test="${admin.category eq vo.category}">
					<tr>
						<td align="right">${vo.bno}&nbsp;</td>
						<td>&nbsp;<a href="detail?bno=${vo.bno}&category=${admin.category}">${vo.title}</a></td>
						<td>&nbsp;${vo.nickname}</td>
						<td>&nbsp; ${vo.dispDate}</td>
						<td align="right"><span class="badge bg-blue">
								${vo.readcnt}</span>&nbsp;</td>
					</tr>
				</c:if>
				</c:forEach>
			</table>
		</div>
		<div class="box-footer">
			<div class="text-center">
				<button id='mainBtn' class="btn-primary">메인으로</button>
			</div>

			<script>
				$(function() {
					$('#mainBtn').on("click", function(event) {
						location.href = "../";
					});
				});
			</script>
		</div>
	</div>






	<div class="box-header with-border">
		 <a href="register?category=${admin.category}"><h3 class="box-title">게시물 작성</h3></a> 
	</div>

	<%@ include file="../include/footer.jsp"%>
</body>
</html>
