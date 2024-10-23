package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.dto.RoomDto;
import com.emse.spring.automacorp.dto.WindowDto;
import com.emse.spring.automacorp.model.RoomEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomDto of(RoomEntity roomEntity) {
        return new RoomDto(
                roomEntity.getId(),
                roomEntity.getName(),
                roomEntity.getFloor(),
                extractCurrentTemperature(roomEntity),
                roomEntity.getTargetTemperature(),
                mapWindows(roomEntity)
        );
    }

    private static Double extractCurrentTemperature(RoomEntity roomEntity) {
        return roomEntity.getCurrentTemperature() == null
                ? null
                : roomEntity.getCurrentTemperature().getValue();
    }

    private static List<WindowDto> mapWindows(RoomEntity roomEntity) {
        return roomEntity.getWindows().stream()
                .map(WindowMapper::of)
                .collect(Collectors.toList());
    }
}
