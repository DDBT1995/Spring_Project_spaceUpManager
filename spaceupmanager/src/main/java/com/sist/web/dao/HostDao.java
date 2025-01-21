package com.sist.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.web.model.Host;

@Repository("hostDao")
public interface HostDao {
    // 정산 리스트
    public List<Host> hostList(Host host);

    // 정산 총게시물 수
    public long hostCount(Host host);
    
    // 상태 값 업데이트
    public int hostStatusUpdate(Host host);
}
