package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dto.ApiGouvAddress;
import com.emse.spring.automacorp.service.AddressSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AddressSearchController {

    private final AddressSearchService addressSearchService;

    public AddressSearchController(AddressSearchService addressSearchService) {
        this.addressSearchService = addressSearchService;
    }

    @GetMapping("/api/address")
    public List<ApiGouvAddress> search(@RequestParam String criteria) {
        List<String> searchTerms = Arrays.asList(criteria.split("-+"));

        return addressSearchService.searchAddress(searchTerms);
    }
}
