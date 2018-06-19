<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상세보기</title>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
					<div>

					</div>
	<section class="content">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">상세보기</h3>
			</div>

			<div class="box-body">
				<div class="form-group">
					<label>제목</label> <input type="text" name="title"
						class="form-control" value="${vo.title}" readonly="readonly" />
				</div>


				<div>
					<label><b>내용</b></label>
				</div>
				<!--  으 %주기 넘어렵다 -->
				<div style="height: 200px">

					<div style="float: left; width: 15%; border: 1px">

						<img class="logindiv_image"
							src="${pageContext.request.contextPath}/userimage/${vo.image}"
							style='height: 200px; width: 100%'> <br />
					</div>
					<div class="form-group" style="float: left; width: 85%">

						<textarea name="content" style="height: 200px"
							class="form-control" readonly="readonly">${vo.content}</textarea>
					</div>

				</div>
				<div class="form-group">
					<label>작성자</label> <input type="text" class="form-control"
						value="${vo.nickname}" readonly="readonly" />
				</div>
			</div>

			<div class="box-footer">
				<button class="btn btn-success" id="mainbtn">메인</button>
				<c:if test="${user.email == vo.email}">
					<button class="btn btn-warning" id="updatebtn">수정</button>
					<button class="btn btn-danger" id="deletebtn">삭제</button>
				</c:if>
				<button class="btn btn-primary" id="listbtn">목록</button>
			</div>
		</div>
	</section>
	<%@ include file="../include/footer.jsp"%>

	<script>
		//메인 버튼을 눌렀을 때 처리
		document.getElementById("mainbtn").addEventListener("click",
				function() {
					location.href = "../";
				});
		//목록 버튼을 눌렀을 때 처리
		document.getElementById("listbtn").addEventListener("click",
				function() {
					location.href = "list?category=${admin.category}&boardname=${admin.boardname}&boardinfo=${admin.boardinfo}&boardpaging=${admin.boardpaging}&page=${criteria.page}&perPageNum=${criteria.perPageNum}";
				});
		<c:if test = "${user.email == vo.email}">
		//수정 버튼을 눌렀을 때 처리
		document.getElementById("updatebtn").addEventListener("click",
				function() {
			location.href = "update?bno=${vo.bno}&page=${criteria.page}&perPageNum=${criteria.perPageNum}&category=${admin.category}";
			});
		</c:if>
	</script>

	<!-- 로그인 한 유저와 작성자가 동일한 경우 -->
	<c:if test="${user.email == vo.email}">
		<link rel="stylesheet"
			href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

		<div id="dialog-confirm" title="정말로 삭제?" style="display: none">
			<p>삭제하시면 복구할 수 없습니다. 그래도 삭제하실 건가요?</p>
		</div>

		<script>
			//삭제 버튼을 눌렀을 때 처리
			document.getElementById("deletebtn").addEventListener(
					"click", function(){
						$("#dialog-confirm").dialog({
						      resizable: false,
						      height: "auto",
						      width: 400,
						      modal: true,
						      buttons: {
						        "삭제": function() {
						          $(this).dialog("close");
						          location.href="delete?bno=${vo.bno}&page=${criteria.page}&perPageNum=${criteria.perPageNum}&category=${admin.category}";
						        },
						        "취소": function() {
						          $(this).dialog("close");
						        }
						      }
						    });
						
			});
		</script>

	</c:if>

	</script>


</body>
</html>