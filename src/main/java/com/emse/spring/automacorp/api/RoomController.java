package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.RoomDao;
import com.emse.spring.automacorp.dao.SensorDao;
import com.emse.spring.automacorp.dto.RoomDto;
import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.mapper.RoomMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomDao roomDao;
    private final SensorDao sensorDao;

    public RoomController(RoomDao roomDao, SensorDao sensorDao) {
        this.roomDao = roomDao;
        this.sensorDao = sensorDao;
    }

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream()
                .map(RoomMapper::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{room_id}")
    public RoomDto findById(@PathVariable("room_id") Long id) {
        return roomDao.findById(id)
                .map(RoomMapper::of)
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<RoomDto> upsert(@RequestBody RoomCommand room) {
        RoomEntity roomEntity = prepareRoomEntity(room);
        updateRoomTemperature(room, roomEntity);
        updateRoomWindows(room, roomEntity);

        RoomEntity savedRoom = roomDao.save(roomEntity);
        return ResponseEntity.ok(RoomMapper.of(savedRoom));
    }

    @DeleteMapping("/{room_id}")
    public void deleteRoom(@PathVariable("room_id") Long id) {
        roomDao.deleteById(id);
    }

    @PutMapping("/{room_id}/openWindows")
    public ResponseEntity<Void> openWindows(@PathVariable("room_id") Long roomId) {
        return changeWindowStatus(roomId, 1.0);
    }

    @PutMapping("/{room_id}/closeWindows")
    public ResponseEntity<Void> closeWindows(@PathVariable("room_id") Long roomId) {
        return changeWindowStatus(roomId, 0.0);
    }

    private RoomEntity prepareRoomEntity(RoomCommand room) {
        return roomDao.findById(room.id())
                .map(existingRoom -> updateExistingRoom(existingRoom, room))
                .orElseGet(() -> createNewRoom(room));
    }

    private RoomEntity updateExistingRoom(RoomEntity roomEntity, RoomCommand room) {
        roomEntity.setName(room.name());
        roomEntity.setFloor(room.floor());
        roomEntity.setTargetTemperature(room.targetTemperature());
        return roomEntity;
    }

    private RoomEntity createNewRoom(RoomCommand room) {
        RoomEntity newRoom = new RoomEntity(room.floor(), room.name(), room.targetTemperature());
        newRoom.setId(room.id());
        return newRoom;
    }

    private void updateRoomTemperature(RoomCommand room, RoomEntity roomEntity) {
        if (room.currentTemperature() != null) {
            SensorEntity tempSensor = new SensorEntity(room.currentTemperature().sensorType(), room.currentTemperature().name());
            tempSensor.setValue(room.currentTemperature().value());
            sensorDao.save(tempSensor);
            roomEntity.setCurrentTemperature(tempSensor);
        }
    }

    private void updateRoomWindows(RoomCommand room, RoomEntity roomEntity) {
        List<WindowEntity> windowEntities = room.windows().stream()
                .map(window -> createWindowEntity(window, roomEntity))
                .collect(Collectors.toList());
        roomEntity.setWindows(windowEntities);
    }

    private WindowEntity createWindowEntity(WindowCommand window, RoomEntity roomEntity) {
        SensorEntity windowSensor = new SensorEntity(window.windowStatus().sensorType(), window.windowStatus().name());
        sensorDao.save(windowSensor);
        return new WindowEntity(window.name(), windowSensor, roomEntity);
    }

    private ResponseEntity<Void> changeWindowStatus(Long roomId, double statusValue) {
        Optional<RoomEntity> roomEntity = roomDao.findById(roomId);
        if (roomEntity.isPresent()) {
            roomEntity.get().getWindows().forEach(window -> window.getWindowStatus().setValue(statusValue));
            roomDao.save(roomEntity.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
