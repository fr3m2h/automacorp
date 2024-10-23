package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.WindowDao;
import com.emse.spring.automacorp.dto.WindowDto;
import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.mapper.WindowMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/windows")
public class WindowController {
    private final WindowDao windowDao;

    public WindowController(WindowDao windowDao) {
        this.windowDao = windowDao;
    }

    @GetMapping
    public List<WindowDto> findAll() {
        return windowDao.findAll()
                .stream()
                .map(WindowMapper::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WindowDto findById(@PathVariable Long id) {
        return windowDao.findById(id)
                .map(WindowMapper::of)
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<WindowDto> create(@RequestBody WindowDto dto) {
        WindowEntity newWindowEntity = createWindowEntity(dto);
        WindowEntity savedEntity = windowDao.save(newWindowEntity);
        return ResponseEntity.ok(WindowMapper.of(savedEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WindowDto> update(@PathVariable Long id, @RequestBody WindowCommand window) {
        return windowDao.findById(id)
                .map(existingWindow -> updateWindowEntity(existingWindow, window))
                .map(updatedWindow -> ResponseEntity.ok(WindowMapper.of(updatedWindow)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        windowDao.deleteById(id);
    }

    private WindowEntity createWindowEntity(WindowDto dto) {
        return new WindowEntity(dto.name(), null, null);
    }

    private WindowEntity updateWindowEntity(WindowEntity existingWindow, WindowCommand window) {
        existingWindow.setName(window.name());
        if (existingWindow.getWindowStatus() != null) {
            existingWindow.getWindowStatus().setName(window.windowStatus().name());
            existingWindow.getWindowStatus().setValue(window.windowStatus().value());
            existingWindow.getWindowStatus().setSensorType(window.windowStatus().sensorType());
        }
        return windowDao.save(existingWindow);
    }
}
