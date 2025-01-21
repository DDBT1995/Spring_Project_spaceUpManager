package com.sist.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.web.model.QuestionReport;


@Repository("questionReportDao")
public interface QuestionReportDao {
    // 정산 리스트
    public List<QuestionReport> questionReportList(QuestionReport questionReport);

    // 정산 총게시물 수
    public long questionReportCount(QuestionReport questionReport);
    
    // 상태 값 업데이트
    public int questionReportStatusUpdateY(long questionReportId);
    
    // 신고 처리, 리뷰 삭제
    public int questionStatusUpdateN(long questionId);
    
    // 신고처리, 회원 정지
    public int questionWriterStatusUpdateS(String guestEmail);
}
