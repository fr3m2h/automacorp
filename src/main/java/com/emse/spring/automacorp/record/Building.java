package com.emse.spring.automacorp.record;

import java.util.List;

public record Building(Long id, Sensor outsideTemperature, List<Room> rooms) {}
