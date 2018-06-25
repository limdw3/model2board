package kr.co.dw.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dw.domain.User;

public interface UserService {
	// email 중복 체크를 위한 메소드
	// 파라미터는 3가지
	// 파라미터 각각을 파라미터로 만드는 경우 : @RequestParam
	// 파라미터를 전부 모아서 만드는 경우: Command 객체
	// 모든 경우에 동일한 파라미터를 사용: HttpServletRequest
	// 파일 업로드가 있는 경우에는 HttpServletRequest 대신에
	// MultipartHttpServletRequest로 변경
	public String emailCheck(HttpServletRequest request);

	// 8.UserService 인터페이스에 회원가입을 위한 메소드를 선언
	// 회원가입을 위한 메소드
	// 파일이 있을 때는 HttpServletRequest 대신에 MultipartHttpServletRequest를 사용
	public Map<String, Object> register(MultipartHttpServletRequest request);

	// 6.UserService 인터페이스에 로그인 관련 메소드 선언
	public User login(HttpServletRequest request);

	// 2.UserService 인터페이스에 위도와 경도를 받아서 주소를 리턴하는 메소드를 선언
	public String address(String loc);
	
	// 회원정보 수정
	public void update(MultipartHttpServletRequest request);
	// 회원정보 삭제
	public void delete(HttpServletRequest request);

	// 회원인증 
	public User regcode(HttpServletRequest request);
	// 비밀번호 변경
	public User resetpw(HttpServletRequest request);
}
