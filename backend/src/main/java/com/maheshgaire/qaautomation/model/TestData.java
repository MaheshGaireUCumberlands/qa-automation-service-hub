package com.maheshgaire.qaautomation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Test data model")
public class TestData {
    
    @Schema(description = "Unique identifier for the test data")
    private String id;
    
    @Schema(description = "Type of test data (user, product, order, etc.)")
    private String type;
    
    @Schema(description = "Dynamic data fields based on type")
    private Map<String, Object> data;
    
    @Schema(description = "Timestamp when the data was generated")
    private LocalDateTime createdAt;

    public TestData() {
        this.createdAt = LocalDateTime.now();
    }

    public TestData(String id, String type, Map<String, Object> data) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}