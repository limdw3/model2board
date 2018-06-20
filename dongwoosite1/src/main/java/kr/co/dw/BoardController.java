package kr.co.dw;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import kr.co.dw.dao.BoardDao;
import kr.co.dw.domain.Admin;
import kr.co.dw.domain.Board;
import kr.co.dw.domain.Criteria;
import kr.co.dw.domain.SearchCriteria;
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
	public String list(SearchCriteria criteria, Model model,HttpServletRequest request) {
		
		Map<String, Object>map = boardService.list(criteria);
		
		model.addAttribute("map", map);
		
		Admin admin = adminService.categoryboard(request);
		

		

	

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
	public String register(SearchCriteria criteria,Model model, MultipartHttpServletRequest request) {

		boardService.register(request);
		// 귀찮아서 위에 리스트 함수 소환!
		Map<String, Object>map = boardService.list(criteria);
		model.addAttribute("map", map);
		return list(criteria, model, request);
	}

	/*
	 * 6.BoardController에 상세보기 요청을 처리하는 메소드를 구현
	 */
	// 게시물 상세보기를 처리
	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	public String detail(SearchCriteria criteria, HttpServletRequest request, Model model ) {

		// 아.. category 정보 가져와야지 목록으로 갈수있어서 ;;
		Admin admin = adminService.categoryboard(request);
		Board board = boardService.detail(request);
		int replycnt = Integer.parseInt(request.getParameter("replycnt"));
		
		request.setAttribute("replycnt", replycnt);
		
		request.setAttribute("criteria", criteria);
		request.setAttribute("admin", admin);
		model.addAttribute("vo", board);
		return "board/detail";
	}

	/*
	 * 3.BoardController 클래스에 게시글을 가져와서 수정보기 화면에 출력하는 메소드를 구현
	 */// 게시물 수정보기를 처리
	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(SearchCriteria criteria,HttpServletRequest request, Model model) {
		Board board = boardService.updateView(request);
	
	
		model.addAttribute("vo", board);
		return "board/update";
	}
	/*9.BoardController 클래스에 수정을 처리해 줄 메소드를 생성*/
	//게시물 수정을 처리해 줄 메소드
	@RequestMapping(value="board/update", method=RequestMethod.POST)
	public String update(SearchCriteria criteria,MultipartHttpServletRequest request, Model model,
			RedirectAttributes attr) {

		boardService.update(request);
		attr.addFlashAttribute("msg", "수정 성공");

		Admin admin =  adminService.categoryboard(request);

		System.out.println(admin.getCategory());
		request.setAttribute("category", admin.getCategory());
		return list(criteria, model, request);
	}
	/*6.BoardController 클래스에 게시글을 삭제하는 메소드를 구현*/
	//게시물 삭제를 처리해주는 메소드
	@RequestMapping(value="board/delete", 
		method=RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model,SearchCriteria criteria,
			RedirectAttributes attr) {
		//서비스 메소드 호출
		boardService.delete(request);
		Admin admin =  adminService.categoryboard(request);
		//메시지 저장
		attr.addFlashAttribute("msg", "게시글 삭제 성공");
		request.setAttribute("category", admin.getCategory());
		//결과 페이지 결정
		return list(criteria, model, request);
	}

}
