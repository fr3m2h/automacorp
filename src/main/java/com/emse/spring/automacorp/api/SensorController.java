package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.SensorDao;
import com.emse.spring.automacorp.dto.SensorDto;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.mapper.SensorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/sensors")
@Transactional
public class SensorController {
    private final SensorDao sensorDao;

    public SensorController(SensorDao sensorDao) {
        this.sensorDao = sensorDao;
    }

    @GetMapping
    public List<SensorDto> findAll() {
        return sensorDao.findAll()
                .stream()
                .map(SensorMapper::of)
                .sorted(Comparator.comparing(SensorDto::name))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public SensorDto findById(@PathVariable Long id) {
        return sensorDao.findById(id)
                .map(SensorMapper::of)
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<SensorDto> create(@RequestBody SensorCommand sensor) {
        SensorEntity newSensorEntity = createSensorEntity(sensor);
        SensorEntity savedEntity = sensorDao.save(newSensorEntity);
        return ResponseEntity.ok(SensorMapper.of(savedEntity));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SensorDto> update(@PathVariable Long id, @RequestBody SensorCommand sensor) {
        return sensorDao.findById(id)
                .map(existingSensor -> updateSensorEntity(existingSensor, sensor))
                .map(updatedSensor -> ResponseEntity.ok(SensorMapper.of(updatedSensor)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        sensorDao.deleteById(id);
    }

    private SensorEntity createSensorEntity(SensorCommand sensor) {
        SensorEntity sensorEntity = new SensorEntity(sensor.sensorType(), sensor.name());
        sensorEntity.setValue(sensor.value());
        return sensorEntity;
    }

    private SensorEntity updateSensorEntity(SensorEntity existingSensor, SensorCommand sensor) {
        existingSensor.setValue(sensor.value());
        existingSensor.setName(sensor.name());
        existingSensor.setSensorType(sensor.sensorType());
        return sensorDao.save(existingSensor);
    }
}
