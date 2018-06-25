package kr.co.dw;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/*5)클라이언트의 요청을 받아서 결과를 json 이나 string
으로 리턴하는 Controller를 만들고 이메일 중복체크 요청이 왔을 때
그 결과를 json으로 리턴하는 메소드를 생성
=>이메일 중복체크는 클라이언트 쪽에서 ajax로 처리하기 위해서*/

import kr.co.dw.service.UserService;

@RestController
public class JSONController {
	@Autowired
	private UserService userService;

	// 이메일 중복 체크 요청 
	@RequestMapping(value = "user/emailcheck", method = RequestMethod.GET)
	public Map<String, Object> emailCheck(HttpServletRequest request) {
		// 존재하는 이메일이면 email에 그 이메일이 저장되고
		// 존재하지 않는 이메일이면 null이 저장됩니다.
		String email = userService.emailCheck(request);
		

		// 리턴할 맵 생성
		Map<String, Object> map = new HashMap<String, Object>();
		// result라는 키에서 email이 null인지 여부 저장
		// 존재하는 이메일이면 false 존재하지 않는 이메일이면 true
		map.put("result", email == null);

		return map;
	}
	
	
	
	
	// 현재 접속 위치 요청 처리
	@RequestMapping(value="address", method=RequestMethod.GET)
	public Map<String, Object> address(String loc){
		Map<String, Object>map = 
			new HashMap<String,Object>();
		//서비스에서 주소를 가져오는 메소드 호출
		String address = userService.address(loc);
		map.put("address", address);
		return map;
	}
}
