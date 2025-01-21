package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.SpaceDao;
import com.sist.web.model.Space;

@Service("spaceService")
public class SpaceService {
    private static Logger logger = LoggerFactory.getLogger(SpaceService.class);

    @Autowired
    private SpaceDao spaceDao;

    // 게스트 리스트
    public List<Space> spacetList(Space space) {
	List<Space> list = null;

	try {
	    list = spaceDao.spaceList(space);
	} catch (Exception e) {
	    logger.error("[spaceService] spacetList Exception", e);
	}

	return list;
    }

    // 총 게스트 수
    public long spaceCount(Space space) {
	long count = 0;

	try {
	    count = spaceDao.spaceCount(space);
	} catch (Exception e) {
	    logger.error("[spaceService] spaceCount Exception", e);
	}

	return count;
    }
    
    // 게스트 상태 업데이트
    public int spaceStatusUpdate(Space space) {
	int result = 0;
	
	try {
	    result = spaceDao.spaceStatusUpdate(space);
	} catch (Exception e) {
	    logger.error("[spaceService] statusUpdate Exception", e);
	}
	
	return result;
    }

}
