package com.sist.web.model;

import java.io.Serializable;

public class ReviewReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long reviewReportId;   // 리뷰 신고 ID, SEQUENCE
    private Long reviewId;         // 리뷰 ID, SEQUENCE
    private String reporterId;     // 신고자 ID, 이메일(게스트 OR 호스트)
    private String reporterType;   // 신고자 타입, ex) 게스트: "G", 호스트: "H"
    private String reportReason;   // 신고 내용
    private String reportDate;     // 신고 날짜
    private String status;         // 처리 여부
    
    private String reviewContent;
    private String reviewWriter;
    
    private long startRow; // 시작페이지(rownum)
    private long endRow; // 끝페이지(rownum)

    // 기본 생성자
    public ReviewReport() {
        this.reviewReportId = 0L;
        this.reviewId = 0L;
        this.reporterId = "";
        this.reporterType = "G"; // 기본값은 게스트(G)
        this.reportReason = "";
        this.reportDate = "";
        this.status = "W";  // 기본값은 처리된 상태
        
        this.reviewContent = "";
        this.reviewWriter = "";
        
        this.startRow = 0;
        this.endRow = 0;
    }

    // Getter와 Setter
    
    
    
    public Long getReviewReportId() {
        return reviewReportId;
    }

    public String getReviewWriter() {
        return reviewWriter;
    }

    public void setReviewWriter(String reviewWriter) {
        this.reviewWriter = reviewWriter;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
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

    public void setReviewReportId(Long reviewReportId) {
        this.reviewReportId = reviewReportId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterType() {
        return reporterType;
    }

    public void setReporterType(String reporterType) {
        this.reporterType = reporterType;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReviewReport{" +
                "reviewReportId=" + reviewReportId +
                ", reviewId=" + reviewId +
                ", reporterId='" + reporterId + '\'' +
                ", reporterType='" + reporterType + '\'' +
                ", reportReason='" + reportReason + '\'' +
                ", reportDate='" + reportDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
