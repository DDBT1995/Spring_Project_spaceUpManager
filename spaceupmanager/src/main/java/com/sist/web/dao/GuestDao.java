package com.sist.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.web.model.Guest;

@Repository("guestDao")
public interface GuestDao {
    // 정산 리스트
    public List<Guest> guestList(Guest guest);

    // 정산 총게시물 수
    public long guestCount(Guest guest);
    
    // 상태 값 업데이트
    public int guestStatusUpdate(Guest guest);
}
