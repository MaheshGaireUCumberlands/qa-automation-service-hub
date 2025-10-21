package com.maheshgaire.qaautomation.mcp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * MCP Resource definition according to Model Context Protocol specification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPResource {
    
    private String uri;
    private String name;
    private String description;
    private String mimeType;
    private Map<String, Object> annotations;
    
    public MCPResource() {}
    
    public MCPResource(String uri, String name, String description, String mimeType) {
        this.uri = uri;
        this.name = name;
        this.description = description;
        this.mimeType = mimeType;
    }
    
    // Factory methods for common resource types
    public static MCPResource testCase(String uri, String name, String description) {
        return new MCPResource(uri, name, description, "application/json");
    }
    
    public static MCPResource testData(String uri, String name, String description) {
        return new MCPResource(uri, name, description, "application/json");
    }
    
    public static MCPResource testReport(String uri, String name, String description) {
        return new MCPResource(uri, name, description, "application/json");
    }
    
    public static MCPResource testMetrics(String uri, String name, String description) {
        return new MCPResource(uri, name, description, "application/json");
    }
    
    // Getters and Setters
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public Map<String, Object> getAnnotations() {
        return annotations;
    }
    
    public void setAnnotations(Map<String, Object> annotations) {
        this.annotations = annotations;
    }
}