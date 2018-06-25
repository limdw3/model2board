package kr.co.dw;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.dw.service.AdminService;
import kr.co.dw.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	// 관리페이지(게시판 생성) 이동 요청
	@RequestMapping(value = "admin/controldiv", method = RequestMethod.GET)
	public void control(Model model) {

	}
	// 게시판 추가 요청 처리
	@RequestMapping(value = "admin/controldiv", method = RequestMethod.POST)
	public String registerboard(HttpServletRequest request, RedirectAttributes attr) {
		adminService.registerboard(request);
		
		
		
		
		return "redirect:/";

	}
}
