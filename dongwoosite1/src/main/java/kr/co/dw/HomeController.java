package kr.co.dw;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.dw.domain.Admin;
import kr.co.dw.service.AdminService;


@Controller
public class HomeController {
	
	@Autowired
	private AdminService adminService;
	// home 이 호출 될 떄 게시판 카테고리를 리스트를 처리한다.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		
		List<Admin> list = new ArrayList<Admin>();
	
		list = adminService.listboard(request);
		
		request.setAttribute("board", list);
		

		return "home";
	}

}
