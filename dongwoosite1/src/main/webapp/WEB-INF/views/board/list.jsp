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
		<!-- 출력할 데이터 개수를 설정하는 select -->
		<div class="box-header with-border">
			<span>목록개수</span>
			<select id="count" class="form-control">
				<option value="1" 
				<c:out value="${map.pageMaker.criteria.perPageNum==1?'selected':'' }"/>
				>1개씩 보기</option>
				<option value="2"
				<c:out value="${map.pageMaker.criteria.perPageNum==2?'selected':'' }"/>
				>2개씩 보기</option>
				<option value="3"
				<c:out value="${map.pageMaker.criteria.perPageNum==3?'selected':'' }"/>
				>3개씩 보기</option>
				<option value="4"
				<c:out value="${map.pageMaker.criteria.perPageNum==4?'selected':'' }"/>
				>4개씩 보기</option>
			</select>
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
				<c:forEach var="vo" items="${map.list}">
				<c:if test="${admin.category eq vo.category}">
					<tr>
						<td align="right">${vo.bno}&nbsp;</td>
						<td>&nbsp;<a href="detail?bno=${vo.bno}&category=${admin.category}&page=${map.pageMaker.criteria.page}&perPageNum=${map.pageMaker.criteria.perPageNum}">${vo.title}</a></td>
						<td>&nbsp;${vo.nickname}</td>
						<td>&nbsp; ${vo.dispDate}</td>
						<td align="right"><span class="badge bg-blue">
								${vo.readcnt}</span>&nbsp;</td>
					</tr>
				</c:if>
				</c:forEach>
			</table>
		</div>
		
		<!-- 페이지 번호 출력 영역 -->
		<div class="box-footer text-center">
			<ul class="pagination">
			
				<c:if test="${map.pageMaker.totalCount > 0 }">
					<!-- 이전 링크 -->
					<c:if test="${map.pageMaker.prev}">
						<li><a href=
						"list?page=${map.pageMaker.startPage-1}&perPageNum=${map.pageMaker.criteria.perPageNum}">이전</a></li>
					</c:if>		
					<!-- 페이지 번호 -->
					<c:forEach var="idx" 
						begin="${map.pageMaker.startPage}" 
						end="${map.pageMaker.endPage}">
						<li 
						<c:out value="${map.pageMaker.criteria.page==idx?'class=active':'' }"/>
						><a href="list?page=${idx}&perPageNum=${map.pageMaker.criteria.perPageNum}&category=${admin.category}">${idx}</a></li>
					</c:forEach>
					<!-- 다음 링크 -->
					<c:if test="${map.pageMaker.next}">
						<li><a href="list?page=${map.pageMaker.endPage+1}&perPageNum=${map.pageMaker.criteria.perPageNum}">다음</a></li>
					</c:if>				
				</c:if>
			</ul>
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
	
	<div class="box-body text-center">
	<select name="searchType" id="searchType">
		<option value="n"
		 <c:out value="${map.pageMaker.criteria.searchType==null?'selected':''}"/>
		>--</option>

		<option value="t"
		 <c:out value="${map.pageMaker.criteria.searchType=='t'?'selected':''}"/>
		>제목</option> 
	
		<option value="c"
		 <c:out value="${map.pageMaker.criteria.searchType=='c'?'selected':''}"/>
		>내용</option>

		<option value="tc"
		 <c:out value="${map.pageMaker.criteria.searchType=='tc'?'selected':''}"/>
		>제목+내용</option>  
	</select>

	<input type="text" name="keyword" id="keyword" 
		value="${map.pageMaker.criteria.keyword}"/>
	<input type="button" class="btn btn-warning" value="검색" 
		id="searchBtn"/>	
</div>

	<%@ include file="../include/footer.jsp"%>
</body>
</html>
