package com.emse.spring.automacorp.dto;


import java.util.List;

public record GouvRespDto(
        String version,
        List<GouvFeatureDto> features
) {

}