package kr.co.dw.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.dw.domain.Board;
import kr.co.dw.domain.Criteria;
import kr.co.dw.domain.SearchCriteria;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	// 글 삽입는 메소드
	public void register(Board board) {
		sqlSession.insert("board.register", board);
	}

	/* 2)게시물 목록을 가져오는 메소드를 수정 */
	// 게시물 전체 목록을 가져오는 메소드
	/* 2)게시물 전체 목록을 가져오는 메소드 */
	public List<Board> list(SearchCriteria criteria) {
		return sqlSession.selectList("board.list", criteria);
	}

	// 3.BoardDao 클래스에 글번호에 해당하는 데이터의 조회수를 1증가시켜 주는 메소드와 글번호에 해당하는 데이터를 가져오는 메소드를 생성

	// 글번호에 해당하는 데이터의 조회수를 1증가시키는 메소드
	public void updatecnt(int bno) {
		sqlSession.update("board.updatecnt", bno);
	}

	// 글번호에 해당하는 데이터를 가져오는 메소드
	public Board detail(int bno) {
		return sqlSession.selectOne("board.detail", bno);
	}

	/* 6.BoardDao 클래스에 게시글 수정을 위한 메소드를 생성 */
	// 글번호에 해당하는 데이터의 수정을 처리하는 메소드
	public void update(Board board) {
		sqlSession.update("board.update", board);
	}

	/* 3.BoardDao 클래스에 게시글을 삭제하는 메소드를 생성 */
	// 글번호에 해당하는 데이터를 삭제를 하는 메소드
	public void delete(int bno) {
		sqlSession.delete("board.delete", bno);
	}

	/*
	 * 4.BoardDao 클래스에 전체 데이터 개수를 가져오는 메소드를 추가하고 게시물 목록을 가졍오는 메소드를 수정 1)전체 데이터 개수를
	 * 가져오는 메소드를 생성
	 */
	// 게시물의 데이터 개수를 가져오는 메소드
	/* 1)데이터 개수를 가져오는 메소드 수정 */
	public int totalCount(SearchCriteria criteria) {
		return sqlSession.selectOne("board.totalcount", criteria);
	}

	/* 3.BoardDao 클래스에 댓글 개수를 가져오는 메소드를 생성 */
	// 댓글 개수를 가져오는 메소드
	public int replycnt(int bno) {
		return sqlSession.selectOne("board.replycnt", bno);
	}

}