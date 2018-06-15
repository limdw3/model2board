package kr.co.dw.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.dw.domain.Board;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 글 삽입는 메소드 
	public void register(Board board){
		sqlSession.insert("board.register", board);
	}
	// 글 리스트 메소드 
	public List<Board> list(){
		
		return sqlSession.selectList("board.list");
		
	}
	
//	3.BoardDao 클래스에 글번호에 해당하는 데이터의 조회수를 1증가시켜 주는 메소드와 글번호에 해당하는 데이터를 가져오는 메소드를 생성

	//글번호에 해당하는 데이터의 조회수를 1증가시키는 메소드
	public void updatecnt(int bno) {
		sqlSession.update("board.updatecnt", bno);
	}
	
	//글번호에 해당하는 데이터를 가져오는 메소드
	public Board detail(int bno) {
		return sqlSession.selectOne("board.detail", bno);
	}
	
	/*6.BoardDao 클래스에 게시글 수정을 위한 메소드를 생성*/
	//글번호에 해당하는 데이터의 수정을 처리하는 메소드
	public void update(Board board) {
		sqlSession.update("board.update", board);
	}
	
/*	3.BoardDao 클래스에 게시글을 삭제하는 메소드를 생성*/
	//글번호에 해당하는 데이터를 삭제를 하는 메소드
	public void delete(int bno) {
		sqlSession.delete("board.delete", bno);
	}

}