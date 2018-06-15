package kr.co.dw.domain;
	
// 게시판 생성,수정,삭제 관리 ( 유저는 생성따로해주니 수정 삭제  유저꺼 끌어다쓸꺼입니다.)
public class Admin {
	private int category;
	private int bigcategory;
	private String boardname; 
	private String boardinfo;
	private int boardpaging;

	
	
	public int getBigcategory() {
		return bigcategory;
	}
	public void setBigcategory(int bigcategory) {
		this.bigcategory = bigcategory;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getBoardname() {
		return boardname;
	}
	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}
	public String getBoardinfo() {
		return boardinfo;
	}
	public void setBoardinfo(String boardinfo) {
		this.boardinfo = boardinfo;
	}
	public int getBoardpaging() {
		return boardpaging;
	}
	public void setBoardpaging(int boardpaging) {
		this.boardpaging = boardpaging;
	}
	@Override
	public String toString() {
		return "Admin [category=" + ", boardname=" + boardname + ", boardinfo=" + boardinfo
				+ ", boardpaging=" + boardpaging + "]";
	}
	
	
}
