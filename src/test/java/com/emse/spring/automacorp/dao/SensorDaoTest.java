package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.SensorEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest //
class SensorDaoTest {
    @Autowired //
    private SensorDao sensorDao;

    @Test
    public void shouldFindASensorById() {
        SensorEntity sensor = sensorDao.getReferenceById(-6L); //
        Assertions.assertThat(sensor.getName()).isEqualTo("Window 2 status room 2");
        Assertions.assertThat(sensor.getValue()).isEqualTo(0.0);
    }
}