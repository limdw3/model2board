<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>

<section class="content">
	<!-- 회원가입 -->
	<!-- method 와 enctype은 파일을 업로드하기 위해서 설정
	파일이 업로드되는 폼은 반드시 method는 post 로
	enctype은 multipart/form-data로 설정
	onsubmit에 함수를 연결한 것은 폼의 데이터를 전송할 때
	유효성 검사를 하기 위해서 입니다. -->
	<form id="registerform" enctype="multipart/form-data" method="post"
		onsubmit="return check()" action="update">
		<p align="center">
		<table border="1" width="50%" height="80%" align='center'>
			<tr>
				<td colspan="3" align="center"><h2>회원 정보</h2></td>
			</tr>
			<tr>
				<td rowspan="5" align="center">
					<p></p> <img id="img" width="100" height="100" border="1"  src="../userimage/${user.image}" /> <br />
					<br /> <input type='file' id="image" name="image" /><br />
					<div id="imageDiv"></div>
				</td>
			</tr>

			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이메일</font></td>
				<td>&nbsp;&nbsp;&nbsp; <!-- onblur는 포커스가 떠날 때
				confirmId()를 호출해서 email 중복 검사를 수행합니다. --> <input type="email"
					name="email" id="email" size="30" maxlength=50 onblur="confirmId()"
					required="required" value="${user.email}" readonly="readonly" /> <!-- 메시지 출력 영역 -->
					<div id="emailDiv"></div>
				</td>
			</tr>

			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호</font></td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" name="pw" id="pw"
					size="20" required="required" value="${user.pw}"/>
					<div id="pwDiv"></div>
				</td>
			</tr>
			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호
						확인</font></td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" id="pwconfirm"
					size="20" required="required"   />
				</td>
			</tr>
			<tr>
				<td width="17%" bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이름</font></td>
				<td>&nbsp;&nbsp;&nbsp; <!-- html5의 pattern 속성을 이용해서 정규식 검사 수행 -->
					<input type="text" name="nickname" size="20"
					pattern="([a-z, A-Z, 가-힣]){2,}" required="required"
					title="닉네임은 문자 2자 이상입니다." value="${user.nickname}" />
				</td>
			</tr>
			<tr>
				<td align="center" colspan="3">
					<p></p> <input type="submit" value="회원정보수정" class="btn btn-warning" />
					 <input type="button" value="회원탈퇴" class="btn btn-warning" id="deletebtn" />
					<input type="button" value="메인으로" class="btn btn-success"
					onclick="javascript:window.location='../'">
					<p></p>
				</td>
			</tr>
		</table>
	</form>
	<br /> <br />
</section>
<%@include file="../include/footer.jsp"%>
<!--  7)register.jsp 파일에서 이메일 중복체크를 위한 스크립트를 추가 -->

<script>
document.getElementById("deletebtn").addEventListener("click",function() {

//	var email = "${user.email}";
	location.href="delete?email=" + "${user.email}";
	
});
</script>
<script>
	
	// 이미지 파트 
	var img = document.getElementById("img");
	var image = document.getElementById("image");

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

	function check() {


		//비밀번호에 입력한 값과 비밀번호 확인에 입력한 값이
		//일치하지 않으면 서버로 전송하지 않도록
		var pw = document.getElementById("pw");
		var pwconfirm = document.getElementById("pwconfirm");
		if (pw.value != pwconfirm.value) {
			alert("두 개의 비밀번호는 일치해야 합니다.");
			document.getElementById("pwDiv").innerHTML = "두 개의 비밀번호는 일치해야 합니다.";
			document.getElementById("pwDiv").style.color = 'red';
			document.getElementById("pw").focus();

			return false;
		}

		//비밀번호는 숫자, 영문자, 특수문자 1개이상으로 8자 이상
		//만들어졌는지 검사
		//정규식 이용 - 숫자, 영문자, 특수문자
		var p1 = /[0-9]/;
		var p2 = /[a-zA-Z]/;
		var p3 = /[~!@#$%^&*()]/;
		if (!p1.test(pw.value) || !p2.test(pw.value) || !p3.test(pw.value)
				|| pw.value.length < 8) {
			alert("비밀번호는 8자이상 숫자, 영문자, 특수문자를 포함해야 합니다.");
			document.getElementById("pwDiv").innerHTML = "비밀번호는 8자이상 숫자, 영문자, 특수문자를 포함해야 합니다.";
			document.getElementById("pwDiv").style.color = 'red';
			document.getElementById("pw").focus();

			return false;
		}

		// 이미지가 만약 jpg , png , gif 가 아니면 
		// 전송 못하게 막음.
		if (imagecheck == false) {
			alert("이미지(jsp,png,gif) 파일만 가능합니다.")
			document.getElementById("imageDiv").innerHTML = "이미지(jsp,png,gif) 파일만 가능합니다.";
			document.getElementById("imageDiv").style.color = 'red';
			document.getElementById("img").focus();
			return false;
		}

	}
</script>



