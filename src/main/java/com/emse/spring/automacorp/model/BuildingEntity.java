package com.emse.spring.automacorp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name =  "SP_BUILDING")
public class BuildingEntity {

    @Id
    @GeneratedValue()
    private Long id;

    @OneToOne
    private SensorEntity outsideTemperature;

    @OneToMany(mappedBy = "building")
    private List<RoomEntity> rooms;

    public BuildingEntity() {}

    public BuildingEntity(SensorEntity outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public SensorEntity getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(SensorEntity outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}
