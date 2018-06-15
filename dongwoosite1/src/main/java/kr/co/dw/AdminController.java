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
	// 관리자페이지이자 지금은 게시판을 생성하는 곳으로 가줌.
	@RequestMapping(value = "admin/controldiv", method = RequestMethod.GET)
	public void control(Model model) {

	}
	// 게시판 추가해줌.
	@RequestMapping(value = "admin/controldiv", method = RequestMethod.POST)
	public String registerboard(HttpServletRequest request, RedirectAttributes attr) {
		adminService.registerboard(request);
		
		
		
		// 삽입, 삭제, 갱신 다음에는 리다이렉트로 이동
		return "redirect:/";

	}
}
