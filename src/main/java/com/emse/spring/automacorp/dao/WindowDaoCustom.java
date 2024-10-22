package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.WindowEntity;

import java.util.List;

public interface WindowDaoCustom {
    List<WindowEntity> findRoomsWithOpenWindows(Long id);

    List<WindowEntity> findAllByRoomName(String roomName);

     int deleteAllByRoomName(String roomName);

     int openOrCloseByRoom(Long id, Boolean open);
}