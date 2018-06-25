package kr.co.dw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dw.dao.AdminDao;
import kr.co.dw.dao.UserDao;
import kr.co.dw.domain.Admin;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Override
	public void registerboard(HttpServletRequest request) {

		// 파라미터 읽기
		// String category = request.getParameter("category"); 자동 시퀀스 사용할꺼다.
		String boardname = request.getParameter("boardname");
		String boardinfo = request.getParameter("boardinfo");
		int boardpaging = Integer.parseInt(request.getParameter("boardpaging"));
		String big = request.getParameter("bigcategory");
		int bigcategory = 0;

		System.out.println(big);
		if (big.equals("men")) {
			bigcategory = 1;
		} else if (big.equals("tek")) {
			bigcategory = 2;
		} else if (big.equals("hob")) {
			bigcategory = 3;
		}
		System.out.println(bigcategory);

		Admin admin = new Admin();

		// admin.setCategory(Integer.parseInt(category));
		admin.setBoardname(boardname);
		admin.setBoardinfo(boardinfo);
		admin.setBoardpaging(boardpaging);
		admin.setBigcategory(bigcategory);

		adminDao.registerboard(admin);

	}

	// 게시판 리스트 보여주기
	@Override
	public List<Admin> listboard(HttpServletRequest request) {
		Admin admin = new Admin();
		List<Admin> list = new ArrayList<Admin>();

		list = adminDao.listboard();

		return list;
	}

	// 요청 카테고리 받아서 카테고리 정보 보내줌.
	@Override
	public Admin categoryboard(HttpServletRequest request) {
		int category = Integer.parseInt(request.getParameter("category"));

		return adminDao.categoryboard(category);
	}

}
