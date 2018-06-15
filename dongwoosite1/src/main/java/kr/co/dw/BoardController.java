package kr.co.dw;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.dw.dao.BoardDao;
import kr.co.dw.domain.Admin;
import kr.co.dw.domain.Board;
import kr.co.dw.service.AdminService;
import kr.co.dw.service.BoardService;
import oracle.net.aso.a;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private AdminService adminService;
	
	// 게시판 목록보기로가줌. ^&
	@RequestMapping(value = "board/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		// return boardDao.list();

		// 오늘 날짜에 작성된 게시글은 시간을
		// 이전에 작성된 게시글은 날짜를 출력하기 위해서 작업

		Admin admin = adminService.categoryboard(request);

		List<Board> list = boardService.list();

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
		}
		request.setAttribute("list", list);

		request.setAttribute("admin", admin);
		return "board/list";
	}

	// 게시글 쓰기 페이지 로 보내주자
	@RequestMapping(value = "board/register", method = RequestMethod.GET)
	public String registerdiv(HttpServletRequest request) {
		// 일단은 기본으로 admin 받고 시작합니다.

		return "board/register";
	}

	// 게시글 삽입하기
	@RequestMapping(value = "board/register", method = RequestMethod.POST)
	public String register(MultipartHttpServletRequest request) {

		boardService.register(request);
		// 귀찮아서 위에 리스트 함수 소환!
		return list(request);
	}

	/*
	 * 6.BoardController에 상세보기 요청을 처리하는 메소드를 구현
	 */
	// 게시물 상세보기를 처리
	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, Model model) {

		// 아.. category 정보 가져와야지 목록으로 갈수있어서 ;;
		Admin admin = adminService.categoryboard(request);
		Board board = boardService.detail(request);

		request.setAttribute("admin", admin);
		model.addAttribute("vo", board);
		return "board/detail";
	}

	/*
	 * 3.BoardController 클래스에 게시글을 가져와서 수정보기 화면에 출력하는 메소드를 구현
	 */// 게시물 수정보기를 처리
	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Model model) {
		Board board = boardService.updateView(request);
		model.addAttribute("vo", board);
		return "board/update";
	}
	/*9.BoardController 클래스에 수정을 처리해 줄 메소드를 생성*/
	//게시물 수정을 처리해 줄 메소드
	@RequestMapping(value="board/update", method=RequestMethod.POST)
	public String update(MultipartHttpServletRequest request, Model model,
			RedirectAttributes attr) {
	
		boardService.update(request);
		attr.addFlashAttribute("msg", "수정 성공");
	
		return list(request);
	}
	/*6.BoardController 클래스에 게시글을 삭제하는 메소드를 구현*/
	//게시물 삭제를 처리해주는 메소드
	@RequestMapping(value="board/delete", 
		method=RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model,
			RedirectAttributes attr) {
		//서비스 메소드 호출
		boardService.delete(request);
		//메시지 저장
		attr.addFlashAttribute("msg", "게시글 삭제 성공");
		//결과 페이지 결정
		return list(request);
	}

}
