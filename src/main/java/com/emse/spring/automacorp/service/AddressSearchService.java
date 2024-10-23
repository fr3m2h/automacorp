package com.emse.spring.automacorp.service;

import com.emse.spring.automacorp.dto.ApiGouvAddress;
import com.emse.spring.automacorp.dto.ApiGouvFeature;
import com.emse.spring.automacorp.dto.ApiGouvResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressSearchService {

    private static final String BASE_URL = "https://api-adresse.data.gouv.fr";
    private static final String SEARCH_ENDPOINT = "/search";
    private static final int RESULT_LIMIT = 15;

    private final RestTemplate restTemplate;

    public AddressSearchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri(BASE_URL)
                .build();
    }

    public List<ApiGouvAddress> searchAddress(List<String> keywords) {
        String query = buildQueryParam(keywords);
        String url = buildSearchUrl(query);

        ApiGouvResponse response = restTemplate.getForObject(url, ApiGouvResponse.class);

        return mapResponseToAddresses(response);
    }

    private String buildQueryParam(List<String> keywords) {
        return String.join("+", keywords);
    }

    private String buildSearchUrl(String query) {
        return UriComponentsBuilder.fromUriString(SEARCH_ENDPOINT)
                .queryParam("q", query)
                .queryParam("limit", RESULT_LIMIT)
                .build()
                .toUriString();
    }

    private List<ApiGouvAddress> mapResponseToAddresses(ApiGouvResponse response) {
        return response.features().stream()
                .map(ApiGouvFeature::properties)
                .collect(Collectors.toList());
    }
}