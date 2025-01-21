package com.sist.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.web.model.Space;

@Repository("spaceDao")
public interface SpaceDao {
    // 정산 리스트
    public List<Space> spaceList(Space space);

    // 정산 총게시물 수
    public long spaceCount(Space space);
    
    // 상태 값 업데이트
    public int spaceStatusUpdate(Space space);
}
