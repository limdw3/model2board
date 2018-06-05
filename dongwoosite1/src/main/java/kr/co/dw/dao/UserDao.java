package kr.co.dw.dao;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.dw.domain.User;

/*2)user.xml 파일에 있는 SQL을 실행할 수 있는 Dao 클래스를 생성하고
작성한 SQL을 호출하는 메소드를 생성*/
@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	// email 중복 체크를 위한 메소드
	public String emailCheck(String email) { // email 받아서 보내야쥐^^
		// 데이터 1개를 받아오는 sql 실행
		// user 는 user.xml 에 있는
		// <mapper namespace="user"> 를 말하고
		// .emailCheck 는 <select id="emailcheck" 를 말한다.
		// 참고로 , email 은 그냥 보내주는 값 말하는거임 ^_^
	
		return sqlSession.selectOne("user.emailcheck", email);
	
	}

	// 회원가입을 위한 메소드
	public void register(User user) {
		System.out.println(user);
		sqlSession.insert("user.register", user);
		System.out.println(user);
	}

	// 5.UserDao 클래스에 로그인 처리를 위한 메소드를 생성
	// 로그인 처리를 위한 메소드
	public User login(String email) {
		return sqlSession.selectOne("user.login", email);
	}
	// 정보수정 처리를 위한메소드
	public void update(User user) {
	
		sqlSession.update("user.update",user);
		
	}
	// 회원 스스로 삭제
	public void delete(String email) {
	
		System.out.println(email);
		sqlSession.delete("user.delete",email);
	}
	// 회원인증하기
	public User regcode(String email) {
		return sqlSession.selectOne("user.regcode", email);
	}
	// 인증이 되면 로그인권한 풀기
	public void regcheck(String email) {
		sqlSession.update("user.regcheck",email);
	}
	// 비번랜덤바꾸기전에 먼저 이메일과 코드 확인
	public String aa(User user) {
		return sqlSession.selectOne("user.aa",user);
	}
	
	// 이메일이랑 코드 로 계정 찾아서  비밀번호 랜덤으로 바꾸기
	public void resetpw(User user) {
	
		sqlSession.update("user.resetpw",user);
		System.out.println("응믕");
		
	}
	

}
