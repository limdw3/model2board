package kr.co.dw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.co.dw.domain.Admin;



public interface AdminService {
	// 게시판 생성
	public void registerboard(HttpServletRequest request);
	// 게시판 리스트
	public List<Admin> listboard(HttpServletRequest request);
	// 하나의 게시판 정보
	public Admin categoryboard(HttpServletRequest request);
	
}
