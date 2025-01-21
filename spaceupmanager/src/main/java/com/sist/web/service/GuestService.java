package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.GuestDao;
import com.sist.web.model.Guest;

@Service("guestService")
public class GuestService {
    private static Logger logger = LoggerFactory.getLogger(GuestService.class);

    @Autowired
    private GuestDao guestDao;

    // 게스트 리스트
    public List<Guest> guesttList(Guest guest) {
	List<Guest> list = null;

	try {
	    list = guestDao.guestList(guest);
	} catch (Exception e) {
	    logger.error("[guestService] guesttList Exception", e);
	}

	return list;
    }

    // 총 게스트 수
    public long guestCount(Guest guest) {
	long count = 0;

	try {
	    count = guestDao.guestCount(guest);
	} catch (Exception e) {
	    logger.error("[guestService] guestCount Exception", e);
	}

	return count;
    }
    
    // 게스트 상태 업데이트
    public int gueestStatusUpdate(Guest guest) {
	int result = 0;
	
	try {
	    result = guestDao.guestStatusUpdate(guest);
	} catch (Exception e) {
	    logger.error("[guestService] statusUpdate Exception", e);
	}
	
	return result;
    }

}
