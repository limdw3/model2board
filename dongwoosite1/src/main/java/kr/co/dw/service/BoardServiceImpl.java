package kr.co.dw.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dw.dao.AdminDao;
import kr.co.dw.dao.BoardDao;
import kr.co.dw.domain.Admin;
import kr.co.dw.domain.Board;
import kr.co.dw.domain.Criteria;
import kr.co.dw.domain.PageMaker;
import kr.co.dw.domain.SearchCriteria;
import kr.co.dw.domain.User;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private AdminDao adminDao;

	// 글 삽입 어마어마하쥬?
	@Override
	public void register(MultipartHttpServletRequest request) {

		// 후,,, 카테고리 넣어서 맞는 DTO 가져오자;;
		int category2 = Integer.parseInt(request.getParameter("category"));

		Admin admin = adminDao.categoryboard(category2);

		int category = admin.getCategory();

		// 파라미터 읽기
		String title = request.getParameter("title");
		category = Integer.parseInt(request.getParameter("category"));
		String content = request.getParameter("content");
		// 파라미터를 이용해서 수행할 작업이 있으면 수행
		String ip = request.getRemoteAddr();
		// 로그인 한 유저의 email과 nickname은 session의
		// user 속성에 있습니다.
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String email = user.getEmail();
		String nickname = user.getNickname();

		// 파일은 읽는 방법이 다름
		MultipartFile image = request.getFile("image");
		System.out.println(image);
		// 파일을 저장할 경로 만들기
		// 파일은 절대경로로만 저장이 가능
		// 프로젝트 내의 userimage 디렉토리의 절대 경로를 만들기
		String uploadPath = request.getRealPath("/userimage");
		// 랜덤한 64자리의 문자열 만들기
		UUID uid = UUID.randomUUID();
		// 원본 파일이름 가져오기
		String filename = image.getOriginalFilename();
		filename = uid + "_" + filename;
		// 업로드할 파일의 실제 경로 만들기
		String filepath = uploadPath + "\\" + filename;

		// Dao의 파라미터로 사용할 객체
		Board board = new Board();
		// 업로드할 파일 객체 만들기
		File f = new File(filepath);
		try {
			// 파일 전송 - 파일 업로드

			image.transferTo(f);

			board.setEmail(email);
			board.setCategory(category);
			board.setIp(ip);
			board.setContent(content);
			board.setTitle(title);
			board.setNickname(nickname);

			board.setImage(filename);

			boardDao.register(board);

		} catch (Exception e) {
			System.out.println("회원가입 실패:" + e.getMessage());
		}

	}

	// 글 목록 보여줌
	@Override
	public Map<String, Object> list(SearchCriteria criteria) {
		// 결과를 저장할 Map을 생성
		Map<String, Object> map = new HashMap<String, Object>();

		// 데이터 가져오기
		List<Board> list = boardDao.list(criteria);
		// 마지막 페이지에 있는 데이터가 1개 밖에 없을 때
		// 그 데이터를 삭제하면 그 페이지의 데이터는 없습니다.
		if (list.size() == 0) {
			// 현재 페이지 번호를 1감소시켜서 데이터를 다시 가져오기
			criteria.setPage(criteria.getPage() - 1);
			list = boardDao.list(criteria);
		}

		// 오늘 날짜에 작성된 게시글은 시간을
		// 이전에 작성된 게시글은 날짜를 출력하기 위해서 작업
		// 오늘 날짜 만들기
		Calendar cal = Calendar.getInstance();
		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		// list의 데이터들을 확인해서 날짜와 시간을 저장
		for (Board board : list) {
			// 작성한 날짜 가져오기
			String regdate = board.getRegdate().substring(0, 10);
			if (today.toString().equals(regdate)) {
				// 시간을 저장 - 분까지 저장
				board.setDispDate(board.getRegdate().substring(11, 16));
			} else {
				// 날짜를 저장
				board.setDispDate(regdate);

			
			}
			// 댓글 개수 가져오기
			int replycnt = boardDao.replycnt(board.getBno());
			// 댓글 개수 저장
			board.setReplycnt(replycnt);
		}
		// 게시물 목록을 Map에 저장
		map.put("list", list);

		// 페이지 번호 목록 만들기
		PageMaker pageMaker = new PageMaker();
		// 현재 페이지와 페이지 당 출력 개수는 저장
		pageMaker.setCriteria(criteria);
		// 전체 데이터 개수 저장
		pageMaker.setTotalCount(boardDao.totalCount(criteria));
		// 페이지 번호 목록 Map에 저장
		map.put("pageMaker", pageMaker);

		return map;
	}

	/* 5.BoardServiceImpl 클래스에 상세보기를 위한 메소드를 구현 */
	@Override
	public Board detail(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// 조회수 1증가시키는 메소드 호출
		boardDao.updatecnt(Integer.parseInt(bno));
		// 데이터 가져오는 메소드를 호출해서 리턴
		Board board = boardDao.detail(Integer.parseInt(bno));
		// 댓글 개수를 가져와서 설정
		board.setReplycnt(boardDao.replycnt(Integer.parseInt(bno)));

		return boardDao.detail(Integer.parseInt(bno));
	}

	/*
	 BoardServiceImpl 클래스에 게시글을 가져와서 수정보기에 사용할 메소드를 구현
	 */@Override
	public Board updateView(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// 데이터 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));
	}

	/* 8.BoardServiceImpl 클래스에 게시글 수정을 처리해 줄 메소드를 구현 */
	@Override
	public void update(MultipartHttpServletRequest request) {
		// System.out.println("서비스 요청");
		// 파라미터 읽기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String bno = request.getParameter("bno");
		// 파라미터를 이용해서 수행할 작업이 있으면 수행
		String ip = request.getRemoteAddr();

		// 파일은 읽는 방법이 다름
		MultipartFile image = request.getFile("image");
		// 파일을 저장할 경로 만들기
		// 파일은 절대경로로만 저장이 가능
		// 프로젝트 내의 userimage 디렉토리의 절대 경로를 만들기
		String uploadPath = request.getRealPath("/userimage");
		// 랜덤한 64자리의 문자열 만들기
		UUID uid = UUID.randomUUID();
		// 원본 파일이름 가져오기
		String filename = image.getOriginalFilename();
		filename = uid + "_" + filename;
		// 업로드할 파일의 실제 경로 만들기
		String filepath = uploadPath + "\\" + filename;

		// Dao의 파라미터로 사용할 객체
		Board board = new Board();
		// 업로드할 파일 객체 만들기
		File f = new File(filepath);
		try {
			// 파일 전송 - 파일 업로드
			image.transferTo(f);

			// Dao 메소드를 호출해야 하는 경우 Dao 메소드의
			// 파라미터를 생성

			board.setIp(ip);
			board.setContent(content);
			board.setTitle(title);
			board.setBno(Integer.parseInt(bno));

			// 데이터베이스에는 파일 이름만 저장
			board.setImage(filename);
			boardDao.update(board);
		} catch (Exception e) {
			System.out.println("회원가입 실패:" + e.getMessage());
		}

		// System.out.println(board);
		// Dao 메소드를 호출
		boardDao.update(board);

		// 리턴할 결과를 만들어서 리턴

	}

	/* 5.BoardServiceImpl 클래스에 게시글을 삭제하는 메소드를 구현 */
	@Override
	public void delete(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// Dao 메소드 호출
		boardDao.delete(Integer.parseInt(bno));
	}

}
