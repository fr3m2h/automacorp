package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.WindowEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class WindowDaoCustomImpl implements WindowDaoCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<WindowEntity> findRoomsWithOpenWindows(Long id) {
        String jpql = "select w from WindowEntity w inner join w.windowStatus s " +
                "where w.room.id = :id and s.value > 0.0 order by w.name";
        return em.createQuery(jpql, WindowEntity.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<WindowEntity> findAllByRoomName(String name){
        String jpql = "select w from WindowEntity w inner join w.room r " +
                "where r.name = :name order by w.name";
        return em.createQuery(jpql, WindowEntity.class)
                .setParameter("name", name)
                .getResultList();
    }

    public int deleteAllByRoomName(String name){
        String jpql = "delete from WindowEntity w inner join w.room r " +
                "where r.name = :name";
        return em.createQuery(jpql)
                .setParameter("name", name).executeUpdate();
    }

    public int openOrCloseByRoom(Long id, Boolean open){
        float value = open ? 1 : 0;
        String jpql = "update SensorEntity s inner join WindowEntity w on w.windowStatus.id = s.id " +
                "set s.value = :value " +
                "where s.sensorType = 'STATUS' and w.id = :id";
        return em.createQuery(jpql)
                .setParameter("value", value).setParameter("id", id).executeUpdate();
    }
}