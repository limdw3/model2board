package kr.co.dw.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;
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

		System.out.println(category);

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
	public List<Board> list() {

		return boardDao.list();

	}

	/*5.BoardServiceImpl 클래스에 상세보기를 위한 메소드를 구현*/
	@Override
	public Board detail(HttpServletRequest request) {
		//파라미터 읽기
		String bno = request.getParameter("bno");
		//조회수 1증가시키는 메소드 호출
		boardDao.updatecnt(Integer.parseInt(bno));
		//데이터 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));
	}
	
	
/*	2.BoardServiceImpl 클래스에 게시글을 가져와서 수정보기에 사용할 메소드를 구현
	*/@Override
	public Board updateView(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// 데이터 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));
	}

	/*8.BoardServiceImpl 클래스에 게시글 수정을 처리해 줄 메소드를 구현*/
	@Override
	public void update(MultipartHttpServletRequest request) {
		//System.out.println("서비스 요청");
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
	

		//System.out.println(board);
		// Dao 메소드를 호출
		boardDao.update(board);

		// 리턴할 결과를 만들어서 리턴
		
	}
	
/*	5.BoardServiceImpl 클래스에 게시글을 삭제하는 메소드를 구현*/
	@Override
	public void delete(HttpServletRequest request) {
		//파라미터 읽기
		String bno = request.getParameter("bno");
		//Dao 메소드 호출
		boardDao.delete(Integer.parseInt(bno));
	}

}
