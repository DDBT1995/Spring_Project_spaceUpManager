package com.sist.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.AdminDao;
import com.sist.web.model.Admin;
import com.sist.web.service.AdminService;

@Service("adminService")
public class AdminService {
    private static Logger logger = LoggerFactory.getLogger(AdminService.class);
    
    @Autowired 
    private AdminDao adminDao;
    
    public Admin adminSelect(String adminId) {
	Admin admin = null;
	try {
	    admin = adminDao.adminSelect(adminId);
	} catch (Exception e) {
	    logger.error("[AdminService] adminSelect Exception", e);
	}
	return admin;
    }
}
