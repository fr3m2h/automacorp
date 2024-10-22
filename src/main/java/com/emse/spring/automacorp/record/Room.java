package com.emse.spring.automacorp.record;

public record Room(Long id, Integer floor, String name, Sensor currentTemperature, Double targetTemperature) {}
