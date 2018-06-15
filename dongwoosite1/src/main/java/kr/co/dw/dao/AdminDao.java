package kr.co.dw.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.dw.domain.Admin;
import kr.co.dw.domain.User;

@Repository
public class AdminDao {
	@Autowired
	private SqlSession sqlSession;

	// 게시판 생성을 위한 메소드
	public void registerboard(Admin admin) {

		sqlSession.insert("admin.registerboard", admin);

		// 홈에서 보여줄 게시판 설정
	}

	public List<Admin> listboard() {

		// 리스트화 시켜서 보내줍니닫.

		return sqlSession.selectList("admin.listboard");

	}
	
	// 하나의 게시판 갈떄 설정된 카테고리 이름 , 페이징 넘겨줌
	public Admin categoryboard(int category) {
		
		return sqlSession.selectOne("admin.categoryboard",category);
		
	}

}
