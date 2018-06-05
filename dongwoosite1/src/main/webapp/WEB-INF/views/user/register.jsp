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
		onsubmit="return check()">
		<!-- onsubmit="return check() 에 대해 애기하자면 
		원래 onsubmit 이 없다고 치자 그러면 원래 처음 이곳으로 보낸 url 로 다시 재전송하는데 이것으로 controller에서는 주로
		같은 url을 get,post를 나누어 쓴다 허나 지금과 같이 유효성을 검사한다음 보내주는 폼을 만들기 위해서 check() 라는 함수를 실행해주는데
		check()함수에서 조건이 하나라도 맞지 않으면은 false 을 리턴하여 우리는 회원가입을 완료시킬 수 가 없다.
		onsubmit 은 참고로  true를 반환받으면은 실행이된다. 여기선 경로가 안써져있음으로 똑같은 전 요청url로 보내줌 그리고 함수의 기본리턴값은 true 이다. -->
		<p align="center">
		<table border="1" width="50%" height="80%" align='center'>
			<tr>
				<td colspan="3" align="center"><h2>회원 가입</h2></td>
			</tr>
			<tr>
				<!--이미지를 삽입하는 file input과 삽입한 이미지의 경로를 찾아 뿌려주는 img enctype="multipart/form-data" 를 쓰는 이유이다.   -->
				<!--  참고로 img 는 imageDiv 를 ajax 로 바로 바로 받아서 변경됨. -->
				<td rowspan="5" align="center">
					<p></p> <img id="img" width="100" height="100" border="1" /> <br />
					<br /> <input type='file' id="image" name="image" /><br />
					<div id="imageDiv"></div>
				</td>
			</tr>

			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이메일</font></td>
				<td>&nbsp;&nbsp;&nbsp; <!-- onblur는 포커스가 떠날 때
				confirmId()를 호출해서 email 중복 검사를 수행합니다. --> <input type="email"
					name="email" id="email" size="30" maxlength=50 onblur="confirmId()"
					required="required" />
					
					<!-- 메시지 출력 영역 -->
					<div id="emailDiv"></div>
					
				</td>
			</tr>

			<tr>
			<!-- 비밀번호는 당연히 유효성검사를 따로 해줌. -->
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호</font></td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" name="pw" id="pw"
					size="20" required="required" />
					<div id="pwDiv"></div>
				</td>
			</tr>
			<tr>
			<!--  비밀번호 확인 파트 -->
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호
						확인</font></td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" id="pwconfirm"
					size="20" required="required" />
				</td>
			</tr>
			<tr>
				<td width="17%" bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이름</font></td>
				<td>&nbsp;&nbsp;&nbsp; <!-- html5의 pattern 속성을 이용해서 정규식 검사 수행 -->
					<input type="text" name="nickname" size="20"
					pattern="([a-z, A-Z, 가-힣]){2,}" required="required"
					title="닉네임은 문자 2자 이상입니다." />
				</td>
			</tr>
			<tr>
				<td align="center" colspan="3">
				<!-- 모든 유효성검사를 이겨낸 자만이 user/register 로 다시 갈수있다. -->
					<p></p> <input type="submit" value="회원가입" class="btn btn-warning" />
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
	//이메일 중복 검사 통과 여부를 저장할 변수
	//전송버튼을 눌렀을 때 이 값이 false이면 전송을 하지 않기 위해서
	var emailcheck = false;
	// 전송버튼 눌렀을때 이 값이 false 이면 전송하지 않기 위함
	// 이미지가 없어도는 되지만 이미지가 아닌 데이터가 들가면 안됨. 그래서 기본은 true 로 설정.
	var imagecheck = true;
	
	

	//email 중복체크를 위한 함수
	function confirmId() {
		//email에 입력된 값 가져오기
		val = document.getElementById("email").value
		//메시지 출력 영역 가져오기
		emaildiv = document.getElementById("emailDiv");

		$.ajax({
			url : 'emailcheck',
			data : {
				'email' : val
			},
			dataType : 'json',
			success : function(data) {

				//이메일이 중복되지 않은 경우
				if (data.result == true) {

					emaildiv.innerHTML = "사용 가능한 이메일입니다.";
					emaildiv.style.color = 'blue';
					emailcheck = true;
				}
				//이메일이 중복된 경우
				else {
					emaildiv.innerHTML = "다른 사람이 사용하거나 사용 불가능한 이메일 입니다."
					emaildiv.style.color = 'red';
					emailcheck = false;
				}
			}
		});

	}
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
	
	// 함수의 retrun 기본값은 true 를 말하고싶었다.
	function check2(){
		return false;
	}
	
	function check() {
		//emailcheck 의 값이 false 이면 서버로 전송하지 않도록
		if (emailcheck == false) {
			alert("이메일 중복 검사를 통과하지 못했습니다.")
			document.getElementById("emailDiv").innerHTML = "이메일 중복 검사를 통과하지 못했습니다.";
			document.getElementById("emailDiv").style.color = 'red';
			document.getElementById("email").focus();
			return false;
		}

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
