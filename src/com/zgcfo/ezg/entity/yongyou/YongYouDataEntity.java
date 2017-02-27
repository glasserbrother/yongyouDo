package com.zgcfo.ezg.entity.yongyou;

import java.io.Serializable;

public class YongYouDataEntity implements Serializable{
	
	private String loginName;
	private String pwd;
	private String bookId;
	private int yongyouId;
	private String currMonth;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}	
	public String getCurrMonth() {
		return currMonth;
	}
	public void setCurrMonth(String currMonth) {
		this.currMonth = currMonth;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public int getYongyouId() {
		return yongyouId;
	}
	public void setYongyouId(int yongyouId) {
		this.yongyouId = yongyouId;
	}
	@Override
	public String toString() {
		return "YongYouDataEntity [loginName=" + loginName + ", pwd=" + pwd
				+ ", bookId=" + bookId + ", yongyouId=" + yongyouId
				+ ", currMonth=" + currMonth + "]";
	}
	
	
}
