package com.emse.spring.automacorp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SP_ROOM")
public class RoomEntity {

    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private SensorEntity currentTemperature;

    @Column(name = "target_temperature")
    private Double targetTemperature;

    @OneToMany(mappedBy = "room")
    private List<WindowEntity> windows;

    @OneToMany(mappedBy = "room")
    private List<HeaterEntity> heaters;

    @ManyToOne
    private BuildingEntity building;

    public RoomEntity() {}

    public RoomEntity(Integer floor, String name, double targetTemperature) {
        this.floor = floor;
        this.name = name;
        this.targetTemperature = targetTemperature;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public List<WindowEntity> getWindows() {
        return windows;
    }

    public void setWindows(List<WindowEntity> windows) {
        this.windows = windows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }

    public SensorEntity getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(SensorEntity currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public List<HeaterEntity> getHeaters() {
        return heaters;
    }

    public void setHeaters(List<HeaterEntity> heaters) {
        this.heaters = heaters;
    }
}
