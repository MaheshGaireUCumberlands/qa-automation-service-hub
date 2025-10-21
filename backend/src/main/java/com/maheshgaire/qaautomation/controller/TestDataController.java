package com.maheshgaire.qaautomation.controller;

import com.maheshgaire.qaautomation.model.TestData;
import com.maheshgaire.qaautomation.service.TestDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/testdata")
@CrossOrigin(origins = "*")
@Tag(name = "Test Data Generation", description = "APIs for generating test data")
public class TestDataController {

    @Autowired
    private TestDataService testDataService;

    @GetMapping(value = "/generate/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate test data", description = "Generate test data of specified type")
    public Flux<TestData> generateTestData(
            @Parameter(description = "Type of test data (user, product, order)")
            @PathVariable String type,
            @Parameter(description = "Number of records to generate")
            @RequestParam(defaultValue = "10") int count) {
        return testDataService.generateTestData(type, count);
    }

    @GetMapping(value = "/templates", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get available templates", description = "Get list of available test data templates")
    public Mono<String[]> getAvailableTemplates() {
        return Mono.just(new String[]{"user", "product", "order", "address", "payment"});
    }
}