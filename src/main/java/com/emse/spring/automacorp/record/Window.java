package com.emse.spring.automacorp.record;

public record Window(Long id, String name, Sensor windowStatus, Long roomId) {
}