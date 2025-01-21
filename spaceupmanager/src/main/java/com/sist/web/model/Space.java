package com.sist.web.model;

import java.io.Serializable;

public class Space implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long spaceId; // SPACE_ID
    private String hostEmail; // HOST_EMAIL
    private int spaceType; // SPACE_TYPE
    private String spaceName; // SPACE_NAME
    private String spaceDesc; // SPACE_DESC
    private String spaceTip; // SPACE_TIP
    private String spaceParking; // SPACE_PARKING
    private int spaceMaxCapacity; // SPACE_MAX_CAPACITY
    private String spaceAddr; // SPACE_ADDR
    private String spaceAddrDesc; // SPACE_ADDR_DESC
    private int spaceCloseDay; // SPACE_CLOSE_DAY
    private String spaceNotice; // SPACE_NOTICE
    private String spaceCloseDayHost; // SPACE_CLOSE_DAY_HOST
    private int spaceStartTime; // SPACE_START_TIME
    private int spaceEndTime; // SPACE_END_TIME
    private int minReservationTime; // MIN_RESERVATION_TIME
    private int spaceHourlyRate; // SPACE_HOURLY_RATE
    private String status; // STATUS
    private String regDate; // REG_DATE
    private String modiDate; // MODI_DATE
    private String delDate; // DEL_DATE
    private int spaceReadCnt; // SPACE_READ_CNT : 공간 조회수


    private String searchValue; // 검색 값

    private long startRow; // 시작페이지(rownum)
    private long endRow; // 끝페이지(rownum)
    
    
    
    // 기본 생성자
    public Space() {
	spaceId = (long) 0;
	hostEmail = "";
	spaceType = 0;
	spaceName = "";
	spaceDesc = "";
	spaceTip = "";
	spaceParking = "";
	spaceMaxCapacity = 0;
	spaceAddr = "";
	spaceAddrDesc = "";
	spaceCloseDay = 0;
	spaceNotice = "";
	spaceCloseDayHost = "";
	spaceStartTime = 0;
	spaceEndTime = 0;
	minReservationTime = 0;
	spaceHourlyRate = 0;
	status = "D";
	regDate = "";
	modiDate = "";
	delDate = "";
	spaceReadCnt = 0;

	searchValue = "";

        startRow = 0;
        endRow = 0;
    }

    // Getter와 Setter
    
    
    public Long getSpaceId() {
	return spaceId;
    }

    public int getSpaceReadCnt() {
        return spaceReadCnt;
    }

    public void setSpaceReadCnt(int spaceReadCnt) {
        this.spaceReadCnt = spaceReadCnt;
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

    public void setSpaceId(Long spaceId) {
	this.spaceId = spaceId;
    }

    public String getHostEmail() {
	return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
	this.hostEmail = hostEmail;
    }

    public int getSpaceType() {
	return spaceType;
    }

    public void setSpaceType(int spaceType) {
	this.spaceType = spaceType;
    }

    public String getSpaceName() {
	return spaceName;
    }

    public void setSpaceName(String spaceName) {
	this.spaceName = spaceName;
    }

    public String getSpaceDesc() {
	return spaceDesc;
    }

    public void setSpaceDesc(String spaceDesc) {
	this.spaceDesc = spaceDesc;
    }

    public String getSpaceTip() {
	return spaceTip;
    }

    public void setSpaceTip(String spaceTip) {
	this.spaceTip = spaceTip;
    }

    public String getSpaceParking() {
	return spaceParking;
    }

    public void setSpaceParking(String spaceParking) {
	this.spaceParking = spaceParking;
    }

    public int getSpaceMaxCapacity() {
	return spaceMaxCapacity;
    }

    public void setSpaceMaxCapacity(int spaceMaxCapacity) {
	this.spaceMaxCapacity = spaceMaxCapacity;
    }

    public String getSpaceAddr() {
	return spaceAddr;
    }

    public void setSpaceAddr(String spaceAddr) {
	this.spaceAddr = spaceAddr;
    }

    public String getSpaceAddrDesc() {
	return spaceAddrDesc;
    }

    public void setSpaceAddrDesc(String spaceAddrDesc) {
	this.spaceAddrDesc = spaceAddrDesc;
    }

    public int getSpaceCloseDay() {
	return spaceCloseDay;
    }

    public void setSpaceCloseDay(int spaceCloseDay) {
	this.spaceCloseDay = spaceCloseDay;
    }

    public String getSpaceNotice() {
	return spaceNotice;
    }

    public void setSpaceNotice(String spaceNotice) {
	this.spaceNotice = spaceNotice;
    }

    public String getSpaceCloseDayHost() {
	return spaceCloseDayHost;
    }

    public void setSpaceCloseDayHost(String spaceCloseDayHost) {
	this.spaceCloseDayHost = spaceCloseDayHost;
    }

    public int getSpaceStartTime() {
	return spaceStartTime;
    }

    public void setSpaceStartTime(int spaceStartTime) {
	this.spaceStartTime = spaceStartTime;
    }

    public int getSpaceEndTime() {
	return spaceEndTime;
    }

    public void setSpaceEndTime(int spaceEndTime) {
	this.spaceEndTime = spaceEndTime;
    }

    public int getMinReservationTime() {
	return minReservationTime;
    }

    public void setMinReservationTime(int minReservationTime) {
	this.minReservationTime = minReservationTime;
    }

    public int getSpaceHourlyRate() {
	return spaceHourlyRate;
    }

    public void setSpaceHourlyRate(int spaceHourlyRate) {
	this.spaceHourlyRate = spaceHourlyRate;
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

}
