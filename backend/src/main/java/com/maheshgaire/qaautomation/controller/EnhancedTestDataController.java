package com.maheshgaire.qaautomation.controller;

import com.maheshgaire.qaautomation.model.enhanced.EnhancedUser;
import com.maheshgaire.qaautomation.model.enhanced.Order;
import com.maheshgaire.qaautomation.service.EnhancedTestDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/testdata")
@CrossOrigin(origins = "*")
@Tag(name = "Enhanced Test Data Generation", description = "Advanced APIs for generating realistic test data with relationships")
public class EnhancedTestDataController {

    @Autowired
    private EnhancedTestDataService enhancedTestDataService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate enhanced users", 
               description = "Generate realistic user data with complete profiles and optional order history")
    public Flux<EnhancedUser> generateEnhancedUsers(
            @Parameter(description = "Number of users to generate")
            @RequestParam(defaultValue = "10") int count,
            @Parameter(description = "Include user order history")
            @RequestParam(defaultValue = "false") boolean includeOrders) {
        
        List<EnhancedUser> users = enhancedTestDataService.generateEnhancedUsers(count, includeOrders);
        return Flux.fromIterable(users);
    }

    @GetMapping(value = "/users-with-orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate users with orders", 
               description = "Generate users with realistic order relationships and transaction history")
    public Flux<EnhancedUser> generateUsersWithOrders(
            @Parameter(description = "Number of users to generate")
            @RequestParam(defaultValue = "5") int userCount,
            @Parameter(description = "Minimum orders per user")
            @RequestParam(defaultValue = "1") int minOrders,
            @Parameter(description = "Maximum orders per user")
            @RequestParam(defaultValue = "5") int maxOrders) {
        
        List<EnhancedUser> users = enhancedTestDataService.generateUsersWithOrders(userCount, minOrders, maxOrders);
        return Flux.fromIterable(users);
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate enhanced orders", 
               description = "Generate realistic order data with items, pricing, and payment information")
    public Flux<Order> generateEnhancedOrders(
            @Parameter(description = "Number of orders to generate")
            @RequestParam(defaultValue = "10") int count,
            @Parameter(description = "Include order items")
            @RequestParam(defaultValue = "true") boolean includeItems) {
        
        List<Order> orders = enhancedTestDataService.generateEnhancedOrders(count, includeItems);
        return Flux.fromIterable(orders);
    }

    @GetMapping(value = "/data-scenarios", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get available data scenarios", 
               description = "Get list of available enhanced data generation scenarios")
    public Mono<Map<String, Object>> getDataScenarios() {
        Map<String, Object> scenarios = new HashMap<>();
        
        scenarios.put("users", Map.of(
            "description", "Enhanced user profiles with addresses and company information",
            "endpoint", "/api/v2/testdata/users",
            "parameters", List.of("count", "includeOrders")
        ));
        
        scenarios.put("users-with-orders", Map.of(
            "description", "Users with realistic order history and transaction data",
            "endpoint", "/api/v2/testdata/users-with-orders",
            "parameters", List.of("userCount", "minOrders", "maxOrders")
        ));
        
        scenarios.put("orders", Map.of(
            "description", "Detailed orders with items, pricing, and payment information",
            "endpoint", "/api/v2/testdata/orders",
            "parameters", List.of("count", "includeItems")
        ));
        
        scenarios.put("features", List.of(
            "Realistic names and addresses using Faker library",
            "Consistent email generation based on names",
            "Realistic pricing and currency handling",
            "Order status progression simulation",
            "Payment method variety with masked card numbers",
            "Product categories and SKU generation",
            "Address relationships (billing vs shipping)",
            "User profile enrichment with company data"
        ));
        
        return Mono.just(scenarios);
    }

    @GetMapping(value = "/sample-formats", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get sample data formats", 
               description = "Preview the structure of enhanced test data")
    public Mono<Map<String, Object>> getSampleFormats() {
        Map<String, Object> samples = new HashMap<>();
        
        // Generate single samples for preview
        List<EnhancedUser> sampleUsers = enhancedTestDataService.generateEnhancedUsers(1, true);
        List<Order> sampleOrders = enhancedTestDataService.generateEnhancedOrders(1, true);
        
        samples.put("user_with_orders", sampleUsers.get(0));
        samples.put("order_with_items", sampleOrders.get(0));
        
        return Mono.just(samples);
    }
}