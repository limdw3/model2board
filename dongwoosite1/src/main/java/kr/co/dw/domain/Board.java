package kr.co.dw.domain;

import java.util.Date;

public class Board {
	private int bno;
	private int category;
	private String image;
	private String title;
	private String content;
	private String regdate;
	private int readcnt;
	private String ip;
	private String email;
	private String nickname;
	private int replycnt;

	public int getReplycnt() {
		return replycnt;
	}

	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}

	// 날짜 및 시간을 출력할 변수
	// 오늘 작성한 글은 시간을 어제 이전에 작성된 글은 날짜를 출력
	// 중요하다... dispDate
	private String dispDate;

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDispDate() {
		return dispDate;
	}

	public void setDispDate(String dispDate) {
		this.dispDate = dispDate;
	}

	@Override
	public String toString() {
		return "Board [bno=" + bno + ", category=" + category + ", image=" + image + ", title=" + title + ", content="
				+ content + ", regdate=" + regdate + ", readcnt=" + readcnt + ", ip=" + ip + ", email=" + email
				+ ", nickname=" + nickname + ", dispDate=" + dispDate + "]";
	}

}
