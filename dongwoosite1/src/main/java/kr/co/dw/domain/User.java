package kr.co.dw.domain;

import java.sql.Date;

public class User {
	private String email; // 회원 이메일
	private String pw;	// 회원 비번
	private String nickname; // 회원 별명
	private String image; // 회원 이미지
	private int grade; // 회원 등급
	private Date regdate; // 가입날짜
	private String code; // 인증 코드 
	private int regcheck; // 인증 되었는지 체크
	
	
	
	
	
	public int getRegcheck() {
		return regcheck;
	}
	public void setRegcheck(int regcheck) {
		this.regcheck = regcheck;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "UserVo [email=" + email + ", pw=" + pw + ", nickname=" + nickname + ", image=" + image + ", grade="
				+ "]";
	}
	
	
}
