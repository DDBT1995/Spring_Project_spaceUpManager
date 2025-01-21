package com.sist.web.model;

import java.io.Serializable;

public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestEmail;
    private String guestPwd;
    private String guestNickname;
    private String guestBirth;
    private String guestTel;
    private String status;
    private String regDate;
    private String modiDate;
    private String delDate;
    
    private String searchValue; // 검색 값

    private long startRow; // 시작페이지(rownum)
    private long endRow; // 끝페이지(rownum)

    public Guest() {
	guestEmail = "";
	guestPwd = "";
	guestNickname = "";
	guestBirth = "";
	guestTel = "";
	status = "Y";
	regDate = "";
	modiDate = "";
	delDate = "";

	searchValue = "";

	startRow = 0;
	endRow = 0;
    }

    public String getGuestEmail() {
	return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
	this.guestEmail = guestEmail;
    }

    public String getGuestPwd() {
	return guestPwd;
    }

    public void setGuestPwd(String guestPwd) {
	this.guestPwd = guestPwd;
    }

    public String getGuestNickname() {
	return guestNickname;
    }

    public void setGuestNickname(String guestNickname) {
	this.guestNickname = guestNickname;
    }

    public String getGuestBirth() {
	return guestBirth;
    }

    public void setGuestBirth(String guestBirth) {
	this.guestBirth = guestBirth;
    }

    public String getGuestTel() {
	return guestTel;
    }

    public void setGuestTel(String guestTel) {
	this.guestTel = guestTel;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getRegDate() {
	return regDate;
    }

    public void setRegDate(String regDate) {
	this.regDate = regDate;
    }

    public String getModiDate() {
	return modiDate;
    }

    public void setModiDate(String modiDate) {
	this.modiDate = modiDate;
    }

    public String getDelDate() {
	return delDate;
    }

    public void setDelDate(String delDate) {
	this.delDate = delDate;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public long getStartRow() {
        return startRow;
    }

    public void setStartRow(long startRow) {
        this.startRow = startRow;
    }

    public long getEndRow() {
        return endRow;
    }

    public void setEndRow(long endRow) {
        this.endRow = endRow;
    }
    
}
