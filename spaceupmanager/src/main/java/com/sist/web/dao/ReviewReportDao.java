package com.sist.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.web.model.ReviewReport;


@Repository("reviewReportDao")
public interface ReviewReportDao {
    // 정산 리스트
    public List<ReviewReport> reviewReportList(ReviewReport reviewReport);

    // 정산 총게시물 수
    public long reviewReportCount(ReviewReport reviewReport);
    
    // 상태 값 업데이트
    public int reviewReportStatusUpdateY(long reviewReportId);
    
    // 신고 처리, 리뷰 삭제
    public int reviewStatusUpdateN(long reviewId);
    
    // 신고처리, 회원 정지
    public int reviewWriterStatusUpdateS(String guestEmail);
}
