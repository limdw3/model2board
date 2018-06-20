package kr.co.dw.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.dw.domain.Reply;

@Repository
public class ReplyDao {
	@Autowired
	private SqlSession sqlSession;

	/* 2.ReplyDao 클래스에 댓글 저장을 위한 메소드를 생성 */
	// 댓글 저장을 위한 메소드
	public int register(Reply reply) {
		return sqlSession.insert("reply.register", reply);
	}

	// 댓글 목록 출력 메소드

	public List<Reply> list(int bno) {
		return sqlSession.selectList("reply.list", bno);
	}

	// 댓글을 삭제하는 메소드
	public int delete(int rno) {
		System.out.println(rno);
		return sqlSession.delete("reply.delete", rno);
	}

	// 댓글을 수정하는 메소드
	public int update(Reply reply) {
		return sqlSession.update("reply.update", reply);
	}

}
