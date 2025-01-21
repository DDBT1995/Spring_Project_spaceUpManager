package com.sist.web.model;

import java.io.Serializable;

public class Host implements Serializable {
    private static final long serialVersionUID = 1L;

    private String hostEmail;       // 호스트 이메일
    private String hostPwd;    // 호스트 비밀번호
    private String hostNickname;    // 호스트 닉네임
    private String hostBirth;       // 호스트 생년월일
    private String hostTel;         // 호스트 전화번호
    private String status;          // 상태 (활성화 여부)
    private String regDate;  // 등록 날짜
    private String modiDate; // 수정 날짜
    private String delDate;  // 삭제 날짜
    
    private String searchValue; // 검색 값

    private long startRow; // 시작페이지(rownum)
    private long endRow; // 끝페이지(rownum)

    public Host() {
        this.hostEmail = "";
        this.hostPwd = "";
        this.hostNickname = "";
        this.hostBirth = "";
        this.hostTel = "";
        this.status = "";
        this.regDate = "";
        this.modiDate = "";
        this.delDate = "";

        this.searchValue = "";

        this.startRow = 0;
        this.endRow = 0;
    }

    // Getter and Setter Methods
    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getHostPassword() {
        return hostPwd;
    }

    public void setHostPassword(String hostPwd) {
        this.hostPwd = hostPwd;
    }

    public String getHostNickname() {
        return hostNickname;
    }

    public void setHostNickname(String hostNickname) {
        this.hostNickname = hostNickname;
    }

    public String getHostBirth() {
        return hostBirth;
    }

    public void setHostBirth(String hostBirth) {
        this.hostBirth = hostBirth;
    }

    public String getHostTel() {
        return hostTel;
    }

    public void setHostTel(String hostTel) {
        this.hostTel = hostTel;
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

    @Override
    public String toString() {
        return "Host{" +
                "hostEmail='" + hostEmail + '\'' +
                ", hostPwd='" + hostPwd + '\'' +
                ", hostNickname='" + hostNickname + '\'' +
                ", hostBirth='" + hostBirth + '\'' +
                ", hostTel='" + hostTel + '\'' +
                ", status='" + status + '\'' +
                ", regDate=" + regDate +
                ", modiDate=" + modiDate +
                ", delDate=" + delDate +
                '}';
    }
}

