package com.sist.web.dao;

import org.springframework.stereotype.Repository;

import com.sist.web.model.Admin;

@Repository("adminDao")
public interface AdminDao {
    public Admin adminSelect(String adminId);

}
