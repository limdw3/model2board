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
	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="login-box well">
					<form accept-charset="UTF-8" role="form" method="post">
						<legend>게시판 생성하기 </legend>

						<div class="form-group">
							<label for="username-email">게시판 이름</label> <input type="text"
								name="boardname" id="boardname" required="required"
								placeholder="게시판 이름을 입력하세요" class="form-control" />
						</div>
						<div class="form-group">
							<label for="password">게시판 간단 설명 </label> <input type="text"
								name="boardinfo" id="boardinfo" placeholder="게시판 설명을 입력하세요"
								class="form-control" />
						</div>
						<div class="form-group">
							<label for="password">게시판 페이징 설정 </label> <input type="text"
								name="boardpaging" id="boardpaging"
								placeholder="게시판 페이정 설정을 해주세요" class="form-control" />
						</div>
						<div class="form-group">
						<label for="password">게시판 카테고리 설정 </label> <br/>
							<input type="radio" name="bigcategory" value="men">인물 
							 <input	type="radio" name="bigcategory" value="tek" checked="checked">기술
							<input type="radio" name="bigcategory" value="hob">취미
						</div>
						<div class="form-group">
							<input type="submit"
								class="btn btn-primary btn-login-submit btn-block m-t-md"
								value="게시판 생성 " />
						</div>


						<div class="form-group">
							<a href="../" class="btn btn-success btn-block m-t-md">메인으로</a>
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
