package com.maheshgaire.qaautomation.service;

import com.maheshgaire.qaautomation.model.TestData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class TestDataService {

    private final Random random = new Random();
    private final String[] firstNames = {"John", "Jane", "Mike", "Sarah", "David", "Emma", "Chris", "Lisa"};
    private final String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
    private final String[] products = {"Laptop", "Phone", "Tablet", "Monitor", "Keyboard", "Mouse", "Headphones", "Camera"};
    private final String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego"};

    public Flux<TestData> generateTestData(String type, int count) {
        return Flux.range(1, count)
                .delayElements(Duration.ofMillis(100)) // Simulate async processing
                .map(i -> createTestDataByType(type));
    }

    private TestData createTestDataByType(String type) {
        String id = UUID.randomUUID().toString();
        Map<String, Object> data = new HashMap<>();

        switch (type.toLowerCase()) {
            case "user":
                data.put("firstName", firstNames[random.nextInt(firstNames.length)]);
                data.put("lastName", lastNames[random.nextInt(lastNames.length)]);
                data.put("email", generateEmail());
                data.put("age", random.nextInt(60) + 18);
                data.put("city", cities[random.nextInt(cities.length)]);
                break;
            case "product":
                data.put("name", products[random.nextInt(products.length)]);
                data.put("price", Math.round((random.nextDouble() * 1000 + 50) * 100.0) / 100.0);
                data.put("category", "Electronics");
                data.put("inStock", random.nextBoolean());
                data.put("sku", "SKU-" + random.nextInt(10000));
                break;
            case "order":
                data.put("orderId", "ORD-" + random.nextInt(100000));
                data.put("customerId", "CUST-" + random.nextInt(10000));
                data.put("total", Math.round((random.nextDouble() * 500 + 10) * 100.0) / 100.0);
                data.put("status", random.nextBoolean() ? "COMPLETED" : "PENDING");
                data.put("items", random.nextInt(5) + 1);
                break;
            case "address":
                data.put("street", (random.nextInt(9999) + 1) + " " + lastNames[random.nextInt(lastNames.length)] + " St");
                data.put("city", cities[random.nextInt(cities.length)]);
                data.put("zipCode", String.format("%05d", random.nextInt(100000)));
                data.put("country", "USA");
                break;
            case "payment":
                data.put("cardNumber", "**** **** **** " + String.format("%04d", random.nextInt(10000)));
                data.put("cardType", random.nextBoolean() ? "VISA" : "MASTERCARD");
                data.put("amount", Math.round((random.nextDouble() * 1000 + 10) * 100.0) / 100.0);
                data.put("currency", "USD");
                break;
            default:
                data.put("message", "Unknown type: " + type);
        }

        return new TestData(id, type, data);
    }

    private String generateEmail() {
        String firstName = firstNames[random.nextInt(firstNames.length)].toLowerCase();
        String lastName = lastNames[random.nextInt(lastNames.length)].toLowerCase();
        String[] domains = {"gmail.com", "yahoo.com", "outlook.com", "company.com"};
        return firstName + "." + lastName + "@" + domains[random.nextInt(domains.length)];
    }
}