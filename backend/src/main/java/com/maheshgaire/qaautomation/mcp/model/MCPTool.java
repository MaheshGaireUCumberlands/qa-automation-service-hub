package com.maheshgaire.qaautomation.mcp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * MCP Tool definition according to Model Context Protocol specification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPTool {
    
    private String name;
    private String description;
    private Map<String, Object> inputSchema;
    
    public MCPTool() {}
    
    public MCPTool(String name, String description, Map<String, Object> inputSchema) {
        this.name = name;
        this.description = description;
        this.inputSchema = inputSchema;
    }
    
    // Factory method for creating a tool with JSON schema
    public static MCPTool create(String name, String description, Map<String, Object> properties, List<String> required) {
        Map<String, Object> schema = Map.of(
            "type", "object",
            "properties", properties,
            "required", required != null ? required : List.of()
        );
        return new MCPTool(name, description, schema);
    }
    
    // Getters and Setters
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
    
    public Map<String, Object> getInputSchema() {
        return inputSchema;
    }
    
    public void setInputSchema(Map<String, Object> inputSchema) {
        this.inputSchema = inputSchema;
    }
}