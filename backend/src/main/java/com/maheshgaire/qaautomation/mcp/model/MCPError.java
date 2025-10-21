package com.maheshgaire.qaautomation.mcp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * MCP Error according to JSON-RPC 2.0 specification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPError {
    
    private int code;
    private String message;
    private Object data;
    
    public MCPError() {}
    
    public MCPError(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public MCPError(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    // Standard JSON-RPC error codes
    public static final int PARSE_ERROR = -32700;
    public static final int INVALID_REQUEST = -32600;
    public static final int METHOD_NOT_FOUND = -32601;
    public static final int INVALID_PARAMS = -32602;
    public static final int INTERNAL_ERROR = -32603;
    
    // MCP-specific error codes
    public static final int TOOL_ERROR = -32000;
    public static final int RESOURCE_ERROR = -32001;
    public static final int PROMPT_ERROR = -32002;
    
    // Factory methods for common errors
    public static MCPError parseError() {
        return new MCPError(PARSE_ERROR, "Parse error");
    }
    
    public static MCPError invalidRequest() {
        return new MCPError(INVALID_REQUEST, "Invalid Request");
    }
    
    public static MCPError methodNotFound(String method) {
        return new MCPError(METHOD_NOT_FOUND, "Method not found: " + method);
    }
    
    public static MCPError invalidParams(String details) {
        return new MCPError(INVALID_PARAMS, "Invalid params: " + details);
    }
    
    public static MCPError internalError(String details) {
        return new MCPError(INTERNAL_ERROR, "Internal error: " + details);
    }
    
    public static MCPError toolError(String details) {
        return new MCPError(TOOL_ERROR, "Tool error: " + details);
    }
    
    public static MCPError resourceError(String details) {
        return new MCPError(RESOURCE_ERROR, "Resource error: " + details);
    }
    
    public static MCPError promptError(String details) {
        return new MCPError(PROMPT_ERROR, "Prompt error: " + details);
    }
    
    // Getters and Setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}