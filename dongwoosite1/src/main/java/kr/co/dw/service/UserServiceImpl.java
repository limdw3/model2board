package kr.co.dw.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dw.dao.UserDao;
import kr.co.dw.domain.User;

/*4)클라이언트의 요청을 처리하는 메소드를 구현할 클
래스를 생성하고 이메일 중복체크를 위한 메소드를 구현*/
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	// 이메일 체크(이것슨 json 아작스)
	@Override
	public String emailCheck(HttpServletRequest request) {

		// 파라미터 읽기
		String email = request.getParameter("email");
		// Dao의 메소드를 호출해서 결과를 전송
		
		return userDao.emailCheck(email);

	}

	// 이건 걍 평범한 회원가입
	@Override
	public Map<String, Object> register(MultipartHttpServletRequest request) {
		
		// 이것은 말이여 랜덤 코드를 작성해주는 함수입니다.
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}
		// 애는 자체제작이라 받아올 필요없다.
		String code = temp.toString();
		
		// 파라미터 읽기
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String pw = request.getParameter("pw");
	
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
		User user = new User();
		// 업로드할 파일 객체 만들기
		File f = new File(filepath);
		try {
			// 파일 전송 - 파일 업로드
			
			image.transferTo(f);
			
			// Dao의 파라미터 만들기
			user.setEmail(email);
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			user.setNickname(nickname);
			user.setCode(code);
			// 데이터베이스에는 파일 이름만 저장
			user.setImage(filename);
			
			userDao.register(user);
			
		
		} catch (Exception e) {
			System.out.println("회원가입 실패:" + e.getMessage());
		}
		// 메일로 보낼 코드와 이메일 가져오기 
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("email", email);
		return map;
	}

	// 7.UserServiceImpl 클래스에 로그인 관련 메소드 구현
	@Override
	public User login(HttpServletRequest request) {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");

		// Dao 메소드 호출
		User user = userDao.login(email);
		if (user != null) {
			// 비밀번호가 일치하면
			if (BCrypt.checkpw(pw, user.getPw()) == true) {
				// 비밀번호만 초기화
				// 비번은 남이 알게되면 안되니깐 보내면 안되요;;
				user.setPw("");
			}
			// 비밀번호가 일치하지 않으면 전부 초기화
			else {
				user = null;
			}
		}
		
		return user;
	}

	// 3.UserServiceImpl 클래스에 위도와 경도를 받아서 주소를 리턴하는 메소드를 구현
	@Override
	public String address(String loc) {
		String[] ar = loc.split("-");
		String addr = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?";
		addr = addr + "x=" + ar[1] + "&y=" + ar[0];

		try {
			// 위의 주소를 가지고 URL 객체를 생성
			URL url = new URL(addr);
			// URL객체를 가지고 HttpURLConnection 객체 만들기
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 인증받기
			con.setRequestProperty("Authorization", "KakaoAK f767f29740a04c38b732dabe1839d6f4");
			// 옵션을 설정
			con.setConnectTimeout(20000);
			con.setUseCaches(false);
			// 줄 단위 데이터 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			// 문자열을 임시로 저장할 객체 만들기
			StringBuilder sb = new StringBuilder();
			while (true) {
				// 한 줄의 데이터 읽기
				String line = br.readLine();
				// 읽은 데이터가 없으면 반복문 종료
				if (line == null) {
					break;
				}
				// 읽은 데이터가 있으면 sb에 추가
				sb.append(line);
			}
			// 연결 해제
			br.close();
			con.disconnect();
			// 데이터 출력
			// System.out.println(sb);
			// JSONObject를 생성
			JSONObject obj = new JSONObject(sb.toString());
			// System.out.println(obj);
			JSONArray imsi = obj.getJSONArray("documents");
			// System.out.println(imsi);
			JSONObject o = imsi.getJSONObject(0);
			String address = o.getString("address_name");
			// System.out.println(address);
			return address;

		} catch (Exception e) {
			System.out.println("주소 가져오기 실패:" + e.getMessage());
		}
		return null;

	}

	@Override
	public void update(MultipartHttpServletRequest request) {
		// 파라미터 읽기
				String email = request.getParameter("email");
				String nickname = request.getParameter("nickname");
				String pw = request.getParameter("pw");
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
				User user = new User();
				// 업로드할 파일 객체 만들기
				File f = new File(filepath);
				try {
					// 파일 전송 - 파일 업로드
					image.transferTo(f);
					// Dao의 파라미터 만들기
					user.setEmail(email);
					user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
					user.setNickname(nickname);

					// 데이터베이스에는 파일 이름만 저장
					user.setImage(filename);
					userDao.update(user);
				} catch (Exception e) {
					System.out.println("회원가입 실패:" + e.getMessage());
				}
}

	@Override
	public void delete(HttpServletRequest request) {
			// 받자
			
			String email = request.getParameter("email");
			
			// 보내자
			userDao.delete(email);
			// 끝
		
	}
	

	
	// 이메일로 회원을 찾은후 DB에 저장된 코드와 메일에 저장된 코드가 같으면.
	@Override
	public User regcode(HttpServletRequest request) {
	
		String check_email = request.getParameter("check_email");
		String check_code = request.getParameter("check_code");
		User user = new User();
		user = userDao.regcode(check_email);
		System.out.println(user.getCode());  
		String code = user.getCode();
	
		
		System.out.println(code.equals(check_code));
		
		if(check_code.trim().toUpperCase().equals(code.trim().toUpperCase())) {
	
			userDao.regcheck(check_email);
		
			return user;
		}
		
		
	
		return null;
	}
	// 이메일 코드 받아서 랜덤 pw 제공.
	@Override
	public User resetpw(HttpServletRequest request) {
	
		
		// 이것은 말이여 랜덤 코드를 작성해주는 함수입니다.
				StringBuffer temp = new StringBuffer();
				Random rnd = new Random();
				for (int i = 0; i < 10; i++) {
				    int rIndex = rnd.nextInt(3);
				    switch (rIndex) {
				    case 0:
				        // a-z
				        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				        break;
				    case 1:
				        // A-Z
				        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				        break;
				    case 2:
				        // 0-9
				        temp.append((rnd.nextInt(10)));
				        break;
				    }
				}
			
		String check_email = request.getParameter("email");
		String check_code = request.getParameter("code");
		User user = new User();
		user.setEmail(check_email);
	
		user.setCode(check_code);

		// 하기전에 이메일과 코드가 맞는 데이터가 있는지 확인
		try {
			String k_email =userDao.aa(user);
			System.out.println(k_email);
			if(!k_email.equals(check_email))
			{
			
			return null;
			}
		} catch (Exception e) {
		
			return null;
		}
		

		
		
		String check_pw = temp.toString();	
		// 변경한 랜덤의 비밀번호일지라도 일단은 암호화해줘야함. 왜냐? 로그인할떄 복호화를 하거등!!
		user.setPw(BCrypt.hashpw(check_pw, BCrypt.gensalt(10)));  
		
		// 찾아서 리셋해주고
		userDao.resetpw(user);
		// 로그인으로 바뀐 비번 가져오고
	user =userDao.login(check_email);
	
		// 비번변경이 끝나면 다시 암호화 하기 전으로 받아줌;
		user.setPw(check_pw);
		
		return user;
	}



}
