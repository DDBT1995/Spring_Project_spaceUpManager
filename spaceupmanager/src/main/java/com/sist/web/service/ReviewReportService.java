package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.ReviewReportDao;
import com.sist.web.model.ReviewReport;

@Service("reviewReportService")
public class ReviewReportService {
    private static Logger logger = LoggerFactory.getLogger(ReviewReportService.class);

    @Autowired
    private ReviewReportDao reviewReportDao;

    // 게스트 리스트
    public List<ReviewReport> reviewReportList(ReviewReport reviewReport) {
	List<ReviewReport> list = null;

	try {
	    list = reviewReportDao.reviewReportList(reviewReport);
	} catch (Exception e) {
	    logger.error("[reviewReportService] reviewReporttList Exception", e);
	}

	return list;
    }

    // 총 게스트 수
    public long reviewReportCount(ReviewReport reviewReport) {
	long count = 0;

	try {
	    count = reviewReportDao.reviewReportCount(reviewReport);
	} catch (Exception e) {
	    logger.error("[reviewReportService] reviewReportCount Exception", e);
	}

	return count;
    }

    // 신고처리, 신고 상태 업데이트
    public int reviewReportStatusUpdateY(long reviewReportId) {
	int result = 0;

	try {
	    result = reviewReportDao.reviewReportStatusUpdateY(reviewReportId);
	} catch (Exception e) {
	    logger.error("[reviewReportService] reviewReportStatusUpdate Exception", e);
	}

	return result;
    }

    // 신고 처리, 리뷰 삭제
    public int reviewStatusUpdateN(long reviewId, long reviewReportId) {
	int result = 0;
	
	try {
	    if(reviewReportDao.reviewStatusUpdateN(reviewId) > 0) {
		result = reviewReportStatusUpdateY(reviewReportId);
	    }
	} catch (Exception e) {
	    logger.error("[reviewReportService] reviewStatusUpdate Exception", e);
	}

	return result;
    }

    // 신고처리, 회원 정지
    public int reviewWriterStatusUpdateS(String guestEmail, long reviewReportId) {
	int result = 0;

	try {
	    if(reviewReportDao.reviewWriterStatusUpdateS(guestEmail) > 0) {
		result = reviewReportStatusUpdateY(reviewReportId);
	    }
	} catch (Exception e) {
	    logger.error("[reviewReportService] reviewWriterStatusUpdateS Exception", e);
	}

	return result;
    }

}
