package com.emse.spring.automacorp;

import com.emse.spring.automacorp.model.BuildingEntity;
import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.record.Building;
import com.emse.spring.automacorp.record.Room;
import com.emse.spring.automacorp.record.Sensor;
import com.emse.spring.automacorp.record.Window;

import java.util.List;

public class RoomMapper {

    // Entity to Model Conversion
    public static Sensor toSensor(SensorEntity sensorEntity) {
        return new Sensor(sensorEntity.getId(), sensorEntity.getName(), sensorEntity.getValue(), sensorEntity.getSensorType());
    }

    public static SensorEntity toSensorEntity(Sensor sensor) {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId(sensor.id());
        sensorEntity.setName(sensor.name());
        sensorEntity.setValue(sensor.value());
        sensorEntity.setSensorType(sensor.sensorType());
        return sensorEntity;
    }

    public static Window toWindow(WindowEntity windowEntity) {
        return new Window(windowEntity.getId(), windowEntity.getName(), toSensor(windowEntity.getWindowStatus()), windowEntity.getRoom().getId());
    }

    public static WindowEntity toWindowEntity(Window window) {
        WindowEntity windowEntity = new WindowEntity();
        windowEntity.setId(window.id());
        windowEntity.setName(window.name());
        windowEntity.setWindowStatus(toSensorEntity(window.windowStatus()));
        return windowEntity;
    }

    public static Room toRoom(RoomEntity roomEntity) {
        return new Room(roomEntity.getId(), roomEntity.getFloor(), roomEntity.getName(), toSensor(roomEntity.getCurrentTemperature()), roomEntity.getTargetTemperature());
    }

    public static RoomEntity toRoomEntity(Room room) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(room.id());
        roomEntity.setFloor(room.floor());
        roomEntity.setName(room.name());
        roomEntity.setCurrentTemperature(toSensorEntity(room.currentTemperature()));
        roomEntity.setTargetTemperature(room.targetTemperature());
        return roomEntity;
    }


    public static Building toBuilding(BuildingEntity buildingEntity) {
        List<Room> rooms = buildingEntity.getRooms().stream()
                .map(RoomMapper::toRoom)
                .toList();
        return new Building(buildingEntity.getId(), toSensor(buildingEntity.getOutsideTemperature()), rooms);
    }

    public static BuildingEntity toBuildingEntity(Building building) {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setId(building.id());
        buildingEntity.setOutsideTemperature(toSensorEntity(building.outsideTemperature()));
        List<RoomEntity> roomEntities = building.rooms().stream()
                .map(RoomMapper::toRoomEntity)
                .toList();
        buildingEntity.setRooms(roomEntities);
        return buildingEntity;
    }

}
