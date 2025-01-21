package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.HostDao;
import com.sist.web.model.Host;

@Service("hostService")
public class HostService {
    private static Logger logger = LoggerFactory.getLogger(HostService.class);

    @Autowired
    private HostDao hostDao;

    // 게스트 리스트
    public List<Host> hosttList(Host host) {
	List<Host> list = null;

	try {
	    list = hostDao.hostList(host);
	} catch (Exception e) {
	    logger.error("[hostService] hosttList Exception", e);
	}

	return list;
    }

    // 총 게스트 수
    public long hostCount(Host host) {
	long count = 0;

	try {
	    count = hostDao.hostCount(host);
	} catch (Exception e) {
	    logger.error("[hostService] hostCount Exception", e);
	}

	return count;
    }
    
    // 게스트 상태 업데이트
    public int hostStatusUpdate(Host host) {
	int result = 0;
	
	try {
	    result = hostDao.hostStatusUpdate(host);
	} catch (Exception e) {
	    logger.error("[hostService] statusUpdate Exception", e);
	}
	
	return result;
    }

}
