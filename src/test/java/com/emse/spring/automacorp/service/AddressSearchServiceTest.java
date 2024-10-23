package com.emse.spring.automacorp.service;

import com.emse.spring.automacorp.dto.ApiGouvAddress;
import com.emse.spring.automacorp.dto.ApiGouvFeature;
import com.emse.spring.automacorp.dto.ApiGouvResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestClientTest(AddressSearchService.class)
class AddressSearchServiceTest {
    @Autowired
    private AddressSearchService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void shouldFindAddresses() throws JsonProcessingException {
        // Arrange
        ApiGouvResponse expectedResponse = prepareMockApiResponse();
        String expectedUrl = constructExpectedUrl("cours", "fauriel");

        setupMockServer(expectedUrl, expectedResponse);

        // Act
        List<ApiGouvAddress> addresses = service.searchAddress(List.of("cours", "fauriel"));

        // Assert
        validateAddressResponse(addresses);
    }

    private ApiGouvResponse prepareMockApiResponse() {
        ApiGouvAddress address = new ApiGouvAddress(
                "ad1",
                "Cours Fauriel 42100 Saint-Étienne",
                "2",
                0.98,
                "42100",
                "42218",
                "Saint Etienne",
                "context",
                "type",
                0.0,
                0.0
        );

        ApiGouvFeature feature = new ApiGouvFeature("type", address);
        return new ApiGouvResponse("v1", List.of(feature));
    }

    private String constructExpectedUrl(String... queryParams) {
        return UriComponentsBuilder
                .fromUriString("/search")
                .queryParam("q", String.join("+", queryParams))
                .queryParam("limit", 15)
                .build()
                .toUriString();
    }

    private void setupMockServer(String expectedUrl, ApiGouvResponse expectedResponse) throws JsonProcessingException {
        this.server
                .expect(MockRestRequestMatchers.requestTo(expectedUrl))
                .andRespond(
                        MockRestResponseCreators.withSuccess(
                                objectMapper.writeValueAsString(expectedResponse),
                                MediaType.APPLICATION_JSON
                        )
                );
    }

    private void validateAddressResponse(List<ApiGouvAddress> addresses) {
        Assertions.assertThat(addresses)
                .hasSize(1)
                .extracting(ApiGouvAddress::city)
                .contains("Saint Etienne");
    }
}
