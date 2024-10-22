package com.emse.spring.automacorp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SP_WINDOW")
public class WindowEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, length=255)  // (4).
    private String name;

    @OneToOne
    @JoinColumn(nullable = false)
    private SensorEntity windowStatus;

    @ManyToOne
    @JoinColumn(nullable = false)
    private RoomEntity room;

    public WindowEntity() {
    }

    public WindowEntity(String name, SensorEntity sensor, RoomEntity room) {
        this.windowStatus = sensor;
        this.name = name;
        this.room = room;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorEntity getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(SensorEntity windowStatus) {
        this.windowStatus = windowStatus;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
}