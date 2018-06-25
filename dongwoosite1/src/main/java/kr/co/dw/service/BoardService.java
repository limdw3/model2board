package kr.co.dw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dw.domain.Board;
import kr.co.dw.domain.Criteria;
import kr.co.dw.domain.SearchCriteria;

public interface BoardService {

	// 글삽입 ( 이미지 파일 떄문에 MultipartHttpServletRequest)
	public void register(MultipartHttpServletRequest request);
	
	// 글 리스트를 보여주기 위한 메소드
	public Map<String,Object> list(SearchCriteria criteria);


	// 게시물 상세보기를 위한 메소드
	public Board detail(HttpServletRequest request);


	// 게시물 수정 보기를 위한 메소드
	public Board updateView(HttpServletRequest request);


	// 게시글 수정을 처리해 줄 메소드를 선언
	public void update(MultipartHttpServletRequest request);

	// 게시글 삭제를 처리해 줄 메소드를 선언
	public void delete(HttpServletRequest request);

}