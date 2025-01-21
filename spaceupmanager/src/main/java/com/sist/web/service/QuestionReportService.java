package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.QuestionReportDao;
import com.sist.web.model.QuestionReport;

@Service("questionReportService")
public class QuestionReportService {
    private static Logger logger = LoggerFactory.getLogger(QuestionReportService.class);

    @Autowired
    private QuestionReportDao questionReportDao;

    // 게스트 리스트
    public List<QuestionReport> questionReportList(QuestionReport questionReport) {
	List<QuestionReport> list = null;

	try {
	    list = questionReportDao.questionReportList(questionReport);
	} catch (Exception e) {
	    logger.error("[questionReportService] questionReporttList Exception", e);
	}

	return list;
    }

    // 총 게스트 수
    public long questionReportCount(QuestionReport questionReport) {
	long count = 0;

	try {
	    count = questionReportDao.questionReportCount(questionReport);
	} catch (Exception e) {
	    logger.error("[questionReportService] questionReportCount Exception", e);
	}

	return count;
    }

    // 신고처리, 신고 상태 업데이트
    public int questionReportStatusUpdateY(long questionReportId) {
	int result = 0;

	try {
	    result = questionReportDao.questionReportStatusUpdateY(questionReportId);
	} catch (Exception e) {
	    logger.error("[questionReportService] questionReportStatusUpdate Exception", e);
	}

	return result;
    }

    // 신고 처리, 리뷰 삭제
    public int questionStatusUpdateN(long questionId, long questionReportId) {
	int result = 0;
	
	try {
	    if(questionReportDao.questionStatusUpdateN(questionId) > 0) {
		result = questionReportStatusUpdateY(questionReportId);
	    }
	} catch (Exception e) {
	    logger.error("[questionReportService] questionStatusUpdate Exception", e);
	}

	return result;
    }

    // 신고처리, 회원 정지
    public int questionWriterStatusUpdateS(String guestEmail, long questionReportId) {
	int result = 0;

	try {
	    if(questionReportDao.questionWriterStatusUpdateS(guestEmail) > 0) {
		result = questionReportStatusUpdateY(questionReportId);
	    }
	} catch (Exception e) {
	    logger.error("[questionReportService] questionWriterStatusUpdateS Exception", e);
	}

	return result;
    }

}
