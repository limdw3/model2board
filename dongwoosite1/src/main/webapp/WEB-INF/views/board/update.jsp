<!-- 4.board 디렉토리에 update.jsp 파일을 만들고 수정 화면을 작성 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<section class="content">
	<div class="box-header">
		<h3 class="box-title">게시판 수정</h3>
	</div>
		<!--  하.. 무조건  파일첨부는 enctype="multipart/form-data" 있어해 -->
	<form role="form" method="post" enctype="multipart/form-data">
		<!-- 데이터 수정을 할 때 기본키의 값이 있어야 해서 필요하고
		작업이 끝나고 결과 페이지로 이동할 때
		상세보기로 이동하려면 글번호가 필요합니다. -->
		<input type="hidden" name="bno" value="${vo.bno}" />
		<div class="box-body">
			<div class="form-group">
				<label>제목</label> <input type="text" name='title'
					class="form-control" value="${vo.title}">
			</div>

			<div>
				<label><b>내용</b></label>
			</div>

			<div style="height: 200px">

				<div style="float: left; width: 15%; border: 1px">

					<img class="logindiv_image" id="img"
						src="${pageContext.request.contextPath}/userimage/${vo.image}"
						style='height: 200px; width: 100%'> <br />
				</div>
				<div class="form-group" style="float: left; width: 85%">

					<textarea name="content" style="height: 200px" class="form-control">${vo.content}</textarea>
				</div>
				
			
			</div>
				<div>
					<input type='file' id="image" name="image" />
				</div>
			<div class="form-group">
				<label>작성자</label> <input type="text" name="nickname"
					value="${user.nickname}" class="form-control" readonly="readonly">
			</div>
		</div>

		<div class="box-footer">
			<button type="submit" class="btn btn-primary">작성완료</button>
		</div>
	</form>
</section>
<%@include file="../include/footer.jsp"%>

<script>
	// 전송버튼 눌렀을때 이 값이 false 이면 전송하지 않기 위함
	// 이미지가 없어도는 되지만 이미지가 아닌 데이터가 들가면 안됨. 그래서 기본은 true 로 설정.
	var imagecheck = true;

	// 이미지 파트 
	var img = document.getElementById("img");
	var image = document.getElementById("image");
	var imgdiv = document.getElementById("imadiv");
	//선택한 파일이름을 저장할 변수
	var filename = "";

	//image 에서 선택이 변경되었을 때 호출되는 함수만들기
	image.addEventListener('change', function() {

		readURL(this);
	});

	function readURL(input) {
		//선택한 파일명 가져오기
		filename = input.files[0].name;
		//그림 파일인지 확인
		//마지막 3글자 가져오기
		var ext = filename.substr(filename.length - 3, filename.length);
		//마지막 3글자가 gif 도 아니고 jpg 도 아니고 png 도 아니면
		if (ext.toLowerCase() != 'gif' && ext.toLowerCase() != 'jpg'
				&& ext.toLowerCase() != 'png') {
			// 이메일 체크를 false 로 바꿔줌.
			imagecheck = false;
			alert('그림 파일을 선택하세요');
			return;
		}

		//그림 파일의 내용 읽기
		// 이메일 체크를 true 로 바꿔줌.
		
		imagecheck = true;
		var reader = new FileReader();
		reader.readAsDataURL(input.files[0]);
		//그림 파일의 내용을 전부 읽으면 img에 출력
		reader.onload = function(e) {

			img.src = e.target.result;
		}
	};
</script>