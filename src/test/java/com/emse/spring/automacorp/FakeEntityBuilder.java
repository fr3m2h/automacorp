package com.emse.spring.automacorp;

import com.emse.spring.automacorp.model.*;

import java.util.List;

import static com.emse.spring.automacorp.model.SensorType.TEMPERATURE;

public class FakeEntityBuilder {

    public static RoomEntity createRoomEntity(Long id, String name, BuildingEntity building) {
        // Sensor is recreated before each test
        RoomEntity entity = new RoomEntity();
        entity.setName(name);
        entity.setCurrentTemperature(createSensorEntity(1L,"TEMP",TEMPERATURE, 23.2));
        entity.setBuilding(building);
        entity.setTargetTemperature(26.4);
        entity.setId(id);
        entity.setWindows(List.of(
                createWindowEntity(id * 10 + 1L, "Window1" + name, entity),
                createWindowEntity(id * 10 + 2L, "Window2" + name, entity)
        ));
        entity.setHeaters(List.of(
                createHeaterEntity(id * 10 + 1L, "Heater1" + name, entity),
                createHeaterEntity(id * 10 + 2L, "Heater2" + name, entity)
        ));
        return entity;
    }

    public static WindowEntity createWindowEntity(Long id, String name, RoomEntity roomEntity) {
        // Sensor is recreated before each test
        WindowEntity windowEntity = new WindowEntity(
                name,
                createSensorEntity(id * 10 + 1L, "Status" + id, SensorType.STATUS, 0.0),
                roomEntity
        );
        windowEntity.setId(id);
        return windowEntity;
    }

    public static HeaterEntity createHeaterEntity(Long id, String name, RoomEntity roomEntity) {
        // Sensor is recreated before each test
        HeaterEntity heaterEntity = new HeaterEntity();
        heaterEntity.setName(name);
        heaterEntity.setStatus(createSensorEntity(id * 10 + 1L, "Status" + id, SensorType.STATUS, 0.0));
        heaterEntity.setRoom(roomEntity);
        heaterEntity.setId(id);
        return heaterEntity;
    }

    public static SensorEntity createSensorEntity(Long id, String name, SensorType type, Double value) {
        // Sensor is recreated before each test
        SensorEntity sensorEntity = new SensorEntity(type, name);
        sensorEntity.setId(id);
        sensorEntity.setValue(value);
        return sensorEntity;
    }

}