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
					<form accept-charset="UTF-8" role="form" method="post"
						action="resetdisp">
						<legend>비밀번호 찾기</legend>
						<div style='color: red'>${msg}</div>
						<div class="form-group">
							<label for="username-email">이메일</label> <input type="email"
								name="email" id="email" required="required"
								placeholder="이메일을 입력하세요" class="form-control" />
						</div>
						<div class="form-group">
							<label for="password">회원코드</label> <input type="text"
								name="code" id="code" placeholder="회원가입시 받은 코드를 입력하세요" class="form-control" />
						</div>
						<div class="form-group">
							<input type="submit"
								class="btn btn-primary btn-login-submit btn-block m-t-md"
								value="비밀번호 발급받기" />
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
