package com.maheshgaire.qaautomation.mcp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * MCP Prompt definition according to Model Context Protocol specification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPPrompt {
    
    private String name;
    private String description;
    private List<MCPPromptArgument> arguments;
    
    public MCPPrompt() {}
    
    public MCPPrompt(String name, String description, List<MCPPromptArgument> arguments) {
        this.name = name;
        this.description = description;
        this.arguments = arguments;
    }
    
    // Factory method for creating prompts
    public static MCPPrompt create(String name, String description, List<MCPPromptArgument> arguments) {
        return new MCPPrompt(name, description, arguments);
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
    
    public List<MCPPromptArgument> getArguments() {
        return arguments;
    }
    
    public void setArguments(List<MCPPromptArgument> arguments) {
        this.arguments = arguments;
    }
    
    /**
     * MCP Prompt Argument
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MCPPromptArgument {
        private String name;
        private String description;
        private boolean required;
        
        public MCPPromptArgument() {}
        
        public MCPPromptArgument(String name, String description, boolean required) {
            this.name = name;
            this.description = description;
            this.required = required;
        }
        
        public static MCPPromptArgument required(String name, String description) {
            return new MCPPromptArgument(name, description, true);
        }
        
        public static MCPPromptArgument optional(String name, String description) {
            return new MCPPromptArgument(name, description, false);
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
        
        public boolean isRequired() {
            return required;
        }
        
        public void setRequired(boolean required) {
            this.required = required;
        }
    }
}