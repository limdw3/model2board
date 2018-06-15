package kr.co.dw;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.dw.domain.User;
import kr.co.dw.service.UserService;
// 루키 루키 넌 나의 루키
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender javaMailSender;
	// 회원가입 페이지로 포워딩
	@RequestMapping(value = "user/register", method = RequestMethod.GET)
	public void register(Model model) {

	}

	// RedirectAttributes는 redirect로 이동할 때 한번만 전달되는 데이터를
	// 보관하는 Spring의 클래스
	// 회원 가입을 처리하는 메소드
	@RequestMapping(value = "user/register", method = RequestMethod.POST)
	public String register(MultipartHttpServletRequest request, RedirectAttributes attr) {
		
		System.out.println(request.getParameter("email")); 
		Map<String, Object>map = userService.register(request);
		
		attr.addFlashAttribute("msg", "회원가입");
		
		// 회원가입시 메일로 기입한 이메일과 자체적으로 랜덤코드를 생성해서 보내줌 
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			mimeMessage.setFrom(new InternetAddress("limdw3@naver.com"));
			mimeMessage.addRecipient(RecipientType.TO,new InternetAddress(map.get("email").toString()));
			mimeMessage.setSubject("동우사이트 코드 발급");
			mimeMessage.setText("<a href='http://localhost:8799/dw/user/regcheck?check_email="+ map.get("email").toString() +"&check_code="+ map.get("code").toString() +"'>"+map.get("code").toString()+"</a>","UTF-8","html");
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			System.out.println(e.getMessage() + "이메일못보내겟어 ㅠㅠ");
			e.printStackTrace();
		}
		
		// 삽입, 삭제, 갱신 다음에는 리다이렉트로 이동
		return "redirect:/";
	}

	// 2.UserController 클래스에 user/login 요청이 왔을 때 user/login.jsp 파일로 포워딩하는 메소드를 생성

	@RequestMapping(value = "user/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "user/login";
	}

	// 8.UserController 클래스에 로그인 요청을 처리하는 메소드를 구현
	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, RedirectAttributes attr, HttpSession session) {
		User user = userService.login(request);
		// 로그인에 실패한 경우
		if (user == null) {
			attr.addFlashAttribute("msg", "없는 이메일이거나 잘못된" + "비밀번호 입니다.");
			return "redirect:login";
		}
		// 로그인에 성공한 경우
		else {
		
			
			if(user.getRegcheck()==0) {
				attr.addFlashAttribute("msg", "이메일인증필수");
				return "redirect:/";
			}else if(user.getRegcheck()==1) {
				
			attr.addFlashAttribute("msg", "로그인성공");
			session.setAttribute("user", user);
			//이전 요청을 가져오기
			Object dest = session.getAttribute("dest");
			//이전 요청이 없으면 시작 페이지로 이동
			if(dest == null) {
				return "redirect:/";
			}
			//이전 요청이 있으면 그 페이지로 이동
			else {
				return "redirect:/" + dest.toString();
			}
	
			}
			return "redirect:/";
		}
	
	}
	
	// 로그아웃 세션초기화
	@RequestMapping(value = "user/logout", method = RequestMethod.POST)
	public String logout(Model model , HttpSession session , RedirectAttributes attr) {
		attr.addFlashAttribute("msg", "로그아웃");
		session.invalidate();
		return "redirect:/";
	}
	// 회원정보 이동
	@RequestMapping(value = "user/detail", method = RequestMethod.GET)
	public String detail(Model model) {
		return "user/detail";
	}
	
	// 회원정보 수정 처리
		@RequestMapping(value = "user/update", method = RequestMethod.POST)
		public String update(Model model, MultipartHttpServletRequest request, RedirectAttributes attr ,HttpSession session) {
			userService.update(request);
			attr.addFlashAttribute("msg", "회원정보수정");
			session.invalidate();
			// 삽입, 삭제, 갱신 다음에는 리다이렉트로 이동
			return "redirect:/";
		}
		// 회원정보 삭제 
		@RequestMapping(value = "user/delete", method = RequestMethod.GET)
		public String delete(Model model , HttpSession session , RedirectAttributes attr, HttpServletRequest request) {
		
			userService.delete(request);
			attr.addFlashAttribute("msg", "회원탈퇴");
			session.invalidate();
			return "redirect:/";
		}
		
	
		// 이메일 링크를 통한 인증하기
		@RequestMapping(value = "user/regcheck", method = RequestMethod.GET)
		public String regcheck(Model model, HttpServletRequest request, RedirectAttributes attr ,HttpSession session) {
			
			User user = userService.regcode(request);
			if(user == null) {
				attr.addFlashAttribute("msg","회원인증실패");
				return "redirect:/";
			}
			else{
							
			attr.addFlashAttribute("msg", "회원인증완료");
			}
			return "redirect:/";
		
			
		}
		
		// 비밀번호 찾기 이동
		@RequestMapping(value = "user/resetdisp", method = RequestMethod.GET)
		public String resetdisp(Model model) {
			return "user/resetdisp";
		}
		// 실제로 reset해주자
		@RequestMapping(value = "user/resetdisp", method = RequestMethod.POST)
		public String resetpw(HttpServletRequest request, RedirectAttributes attr) {
			
			
			User user = new User();
			user = userService.resetpw(request);
			if(user == null) {
				attr.addFlashAttribute("msg", "이메일이나 코드가 맞지 않습니다.");
				return "redirect:/";
			}
			attr.addFlashAttribute("msg", "비밀번호초기화");
			
			// 회원가입시 메일로 기입한 이메일과 자체적으로 랜덤코드를 생성해서 보내줌 
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			try {
				mimeMessage.setFrom(new InternetAddress("limdw3@naver.com"));
				mimeMessage.addRecipient(RecipientType.TO,new InternetAddress(user.getEmail()));
				mimeMessage.setSubject(user.getEmail() + "님의 임시 비밀번호");
				mimeMessage.setText("<h4>"+ user.getPw() +"</h4>","UTF-8","html");
				
				javaMailSender.send(mimeMessage);
			} catch (MessagingException e) {
				System.out.println(e.getMessage() + "이메일못보내겟어 ㅠㅠ");
				e.printStackTrace();
			}
			
			// 삽입, 삭제, 갱신 다음에는 리다이렉트로 이동
			return "redirect:/";
		}

		
}
