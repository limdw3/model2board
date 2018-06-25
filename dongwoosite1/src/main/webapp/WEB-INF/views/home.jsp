<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 현재 파일 위치에서 include 디렉토리의 header.jsp 파일의
코드를 가져와서 삽입 -->
<%@ include file="include/header.jsp"%>


<!--
 main 
이라는 div안에 있는 .main_common1 과  .main_common2 로 나눈다.-->
<style>
/* <!--채팅창 스타일 설정 --!> */
#userArea {
	width: 100%;
	height: 100px;
	overflow-y: auto;
	border: 1px solid black;
	overflow-y: auto;
}

#chatArea {
	width: 100%;
	height: 200px;
	overflow-y: auto;
	border: 1px solid black;
	overflow-y: auto;
}

.main {
	
}

.main_common1 {
	display: inline-block;
	float: left;
	width: 80%;
	height: 0;
	padding-bottom: 80%;
}

.main_common2 {
	display: inline-block;
	float: right;
	width: 20%;
	height: 0;
	padding-bottom: 80%;
}
</style>


<style>
.logindiv {
	
}

.logindiv_image {
	display: inline-block;
	float: left;
	width: 50%;
	height: 50%;
}

.logindiv_info {
	display: inline-block;
	float: right;
	width: 50%;
	height: 0;
}

<!--
요기는 게시판 목록 스타일 지정 --!> table.type04 {
	border-collapse: separate;
	border-spacing: 1px;
	text-align: left;
	line-height: 1.5;
	border-top: 1px solid #ccc;
	margin: 20px 10px;
}

table.type04 th {
	width: 150px;
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
}

table.type04 td {
	width: 350px;
	padding: 10px;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
}
</style>
<!-- 모든 공간을 할당하는 div -->
<div class="main">

	<!-- 전체게시판과 지도를 가진 메인 div -->
	<div class="main_1 main_common1">
		<!--  overflow:auto div 높이 넘어가면 스크롤 생김 
		float 가로배치를 위해서 사용  -->

		<div style="overflow: auto; float: left; width: 33%; height: 500px;"">

			<table class="type04">
				<tr scope="row">
					<th bgcolor="#E6E6E6">인물 카테고리</th>

				</tr>

				<c:forEach var="vo" items="${board}">
					<c:if test="${vo.bigcategory==1}">
						<tr>
							<!-- 이름을 클릭하면 board/list 로 보내는 태그에 보드이름 , 보드 pk 번호(d_board 의 외래키) , 페이징 설정을 태그에 넣어 보낸다.  -->
							<td align="center"><a
								href="board/list?category=${vo.category}&boardname=${vo.boardname}&boardinfo=${vo.boardinfo}&boardpaging=${vo.boardpaging}">
									${vo.boardname}&nbsp;</a></td>


						</tr>
					</c:if>

				</c:forEach>

			</table>
		</div>
		<div style="float: left; width: 33%">
			<table class="type04">
				<tr scope="row">
					<th bgcolor="#A4A4A4">기술 카테고리</th>

				</tr>
				<c:forEach var="vo" items="${board}">
					<c:if test="${vo.bigcategory==2}">
						<tr>
							<!-- 이름을 클릭하면 board/list 로 보내는 태그에 보드이름 , 보드 pk 번호(d_board 의 외래키) , 페이징 설정을 태그에 넣어 보낸다.  -->
							<td align="center"><a
								href="board/list?category=${vo.category}&boardname=${vo.boardname}&boardinfo=${vo.boardinfo}&boardpaging=${vo.boardpaging}">
									${vo.boardname}&nbsp;</a></td>


						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		<div style="float: left; width: 33%">
			<table class="type04">
				<tr scope="row">
					<th width="11%" bgcolor="#E6E6E6">취미 카테고리</th>

				</tr>
				<c:forEach var="vo" items="${board}">
					<c:if test="${vo.bigcategory==3}">
						<tr>
							<!-- 이름을 클릭하면 board/list 로 보내는 태그에 보드이름 , 보드 pk 번호(d_board 의 외래키) , 페이징 설정을 태그에 넣어 보낸다.  -->
							<td align="center"><a
								href="board/list?category=${vo.category}&boardname=${vo.boardname}&boardinfo=${vo.boardinfo}&boardpaging=${vo.boardpaging}">
									${vo.boardname}&nbsp;</a></td>


						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>

	<!-- 로그인 , 검색순위 , 채팅 등   서브 메뉴 div -->
	<div class="main_2 main_common2">
		<!-- 로그인(Login) 이 되어잇지 않을 떄(user 못받은 상태) 보여줄 DIV(로그인 창[이메일,비밀번호 입력],회원가입,비밀번호 찾기 버튼 )을 활성화 시켜준다.-->
		<c:if test="${user == null}">
			<div align="left">
				<div class="row">


					<div class="login-box well">
						<!-- user/login 으로 post 해주는 로그인 폼 -->
						<form accept-charset="UTF-8" role="form" method="post"
							action="user/login">
							<legend>로그인</legend>
							<!-- Controller 에서 이벤트가 작동 후 보내는 신호 ${msg} 를 담을 div -->
							<div style='color: red'>${msg}</div>
							<div class="form-group">
								<label for="username-email">이메일</label> <input type="email"
									name="email" id="email" required="required"
									placeholder="이메일을 입력하세요" class="form-control" />
							</div>
							<div class="form-group">
								<label for="password">비밀번호</label> <input type="password"
									name="pw" id="pw" placeholder="비밀번호를 입력하세요"
									class="form-control" />
							</div>
							<div class="form-group">
								<input type="submit"
									class="btn btn-primary btn-login-submit btn-block m-t-md"
									value="로그인" />
							</div>
							<!--  user/register 회원가입 창으로 이동하는 버튼 -->
							<div class="form-group">
								<a href="user/register" class="btn btn-warning btn-block m-t-md">회원가입</a>
							</div>
							<!--  user/resetdisp 비밀번호 찾기 창으로 이동하는 버튼 -->
							<div class="form-group">
								<a href="user/resetdisp"
									class="btn btn-success btn-block m-t-md">비밀번호 찾기</a>
							</div>

						</form>
					</div>


				</div>
			</div>
		</c:if>

		<!-- 로그인(Login)  되었다면  user 를 받아  보여줄 DIV(닉네임, 등급 , 이미지, 접속한 위치[구글맵API])을 활성화 시켜준다.-->
		<c:if test="${user != null}">
			<div align="left">



				<div class="login-box well">
					<!-- submit 이 user/logout 으로 통하는 post 형식의 폼 -->
					<form accept-charset="UTF-8" role="form" method="post"
						action="user/logout">
						<!-- 현재위치를 나타내기 위해 address 라는 아이디를 가진 div  -->
						<legend>
							<div class="box-header with-border" id="address"></div>
						</legend>
						<!-- 로그인이 되어서 받은 user 의 정보를 나타내는 div -->
						<div class="logindiv">
							<!-- userimage 파일에 저장된 이미지[user 데이터에 저장된 파일이름 ]를 가져오는 div -->
							<img class="logindiv_image"
								src="${pageContext.request.contextPath}/userimage/${user.image}"
								width=50% height=50% />
							<div class="logindiv_info">
								<h4>닉네임 : ${user.nickname}님</h4>
								<br />
								<h4>
									<!-- 유저의 등급이 0 이면 일반인 1 이면 관리자임. 1 일떄는 게시판과 회원을 관리하는 창으로 넘어가는 버튼을 추가해줄꺼임. -->
									등 급 :
									<c:if test="${user.grade == 0}">
							일반인
							</c:if>
									<c:if test="${user.grade == 1}">
							관리자님
							</c:if>
								</h4>
							</div>



						</div>

						<!--  user/logout 로그아웃 submit -->
						<div class="form-group">
							<input type="submit"
								class="btn btn-primary btn-login-submit btn-block m-t-md"
								value="로그아웃" />
						</div>
						<!-- user/etail 개인정조 창으로 이동 -->
						<div class="form-group">
							<c:if test="${user.grade == 0}">
								<a href="user/detail" class="btn btn-warning btn-block m-t-md">개인정보</a>
							</c:if>
							<c:if test="${user.grade == 1}">
								<a href="admin/controldiv"
									class="btn btn-warning btn-block m-t-md">관리 페이지</a>
							</c:if>
						</div>


					</form>
				</div>




			</div>
		</c:if>

	</div>

</div>




<!-- 회원정보 삽입 완료 알림 메시지 -->
<c:if test="${msg != null && msg == '회원가입' }">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-message").dialog({
				modal : true,
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	</script>

	<div id="dialog-message" title="회원가입 성공">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> 마지막으로 본인의 이메일로 가서
			본인인증을 해주십쇼
		</p>
	</div>
</c:if>

<!-- 인증되지 않음 알림 메시지 -->
<c:if test="${msg != null && msg == '이메일인증필수' }">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-message").dialog({
				modal : true,
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	</script>

	<div id="dialog-message" title="회원인증 필수">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> 이메일 인증 부탁드립니다.
		</p>
	</div>
</c:if>

<!-- 이메일 인증완료 알림 메시지 -->
<c:if test="${msg != null && msg == '회원인증완료' }">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-message").dialog({
				modal : true,
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	</script>

	<div id="dialog-message" title="이메일 인증에 성공하쎳습니다.">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> 이메일 인증에 성공하셨습니다.
		</p>
	</div>
</c:if>
<!-- 로그인 완료 알림 메시지 -->
<c:if test="${msg != null && msg == '로그인성공' }">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			// dialog 메세지를 만들어주겠다.
			$("#dialog-message").dialog({
				modal : true, // 확인 버튼 만 있게 만든다.
				buttons : {
					Ok : function() { // OK 누르면 this(이 ) 다이아로그 메세지 창을 끄겠다.
						$(this).dialog("close");
					}
				}
			});
		});
	</script>
	<!-- 다이아로그 메세지 타이틀 설정 -->
	<div id="dialog-message" title="로그인성공">
		<!-- 다이아로그 메시지의 내용 -->
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> 로그인에 성공했습니다.
		</p>
	</div>
</c:if>

<!-- 회원정보 수정 완료 알림 메시지 -->
<c:if test="${msg != null && msg == '회원정보수정' }">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-message").dialog({
				modal : true,
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	</script>회원인증완료

	<div id="dialog-message" title="회원정보수정 성공">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> 회원정보수정에 성공하셨습니다.
		</p>
	</div>
</c:if>




<script>
	//setInterval 타이머 설정이  가능한  함수 - 정해진거임-
	setInterval(function() {

		//현재 위치를 조사하는 함수
		navigator.geolocation.getCurrentPosition(function(position) {

			//위도 경도를 담은 변수 설정 "-" 봐도 json 이 떠오른다.
			loc = position.coords.latitude + "-" + position.coords.longitude;
			//ajax 요청하기
			$.ajax({
				url : "address", // url 요청 address
				data : {
					"loc" : loc
				//데이터는 loc 를 "loc"에 담아 보냄
				},
				dataType : "json", // 타입은 json 형식으로. 
				success : function(data) {
					// 데이터의 주고받음이 성공하면  address 라는 아이디를 가진 div 에게 html 형식을 추가한다. 가져온 data에 담긴 address 라는 객체를 <h3> 안에 담아.
					document.getElementById("address").innerHTML = "<h3>"
							+ data.address + "</h3>";
				}
			});

		});
		// 10초에 한번씩 동작한다는 뜻.
	}, 10000);
</script>



<!-- 아래 </body> ,</html> 같은 모든 jsp 파일에 공통된 속성을 footer 라는 jsp파일을 include 해서 간편히 사용한다. -->
<%@ include file="include/footer.jsp"%>