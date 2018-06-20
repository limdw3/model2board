package kr.co.dw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dw.domain.Board;
import kr.co.dw.domain.Criteria;
import kr.co.dw.domain.SearchCriteria;

public interface BoardService {

	// 글삽입해요~ 이미지 떄매 멀티싸염~
	public void register(MultipartHttpServletRequest request);
	
	// 글 리스트 
	public Map<String,Object> list(SearchCriteria criteria);

	// 4.BoardService 인터페이스에 상세보기를 처리할 메소드를 선언
	// 게시물 상세보기를 위한 메소드
	public Board detail(HttpServletRequest request);

	/* 1.BoardService 인터페이스에 게시글을 가져와서 수정보기에 사용할 메소드를 선언 */
	// 게시물 수정 보기를 위한 메소드
	public Board updateView(HttpServletRequest request);

	/* 7.BoardService 인터페이스에 게시글 수정을 처리해 줄 메소드를 선언 */
	// 게시글 수정을 처리해 줄 메소드를 선언
	public void update(MultipartHttpServletRequest request);

	// 게시글 삭제를 처리해 줄 메소드를 선언
	public void delete(HttpServletRequest request);

}