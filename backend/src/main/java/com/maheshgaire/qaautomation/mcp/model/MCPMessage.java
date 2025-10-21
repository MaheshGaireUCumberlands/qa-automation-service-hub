package com.maheshgaire.qaautomation.mcp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Base MCP message according to Model Context Protocol specification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPMessage {
    
    @JsonProperty("jsonrpc")
    private String jsonrpc = "2.0";
    
    private String id;
    private String method;
    private Map<String, Object> params;
    private Object result;
    private MCPError error;
    
    public MCPMessage() {}
    
    public MCPMessage(String id, String method, Map<String, Object> params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }
    
    // Request constructor
    public static MCPMessage request(String id, String method, Map<String, Object> params) {
        return new MCPMessage(id, method, params);
    }
    
    // Response constructor
    public static MCPMessage response(String id, Object result) {
        MCPMessage message = new MCPMessage();
        message.setId(id);
        message.setResult(result);
        return message;
    }
    
    // Error response constructor
    public static MCPMessage error(String id, MCPError error) {
        MCPMessage message = new MCPMessage();
        message.setId(id);
        message.setError(error);
        return message;
    }
    
    // Notification constructor
    public static MCPMessage notification(String method, Map<String, Object> params) {
        MCPMessage message = new MCPMessage();
        message.setMethod(method);
        message.setParams(params);
        return message;
    }
    
    // Getters and Setters
    public String getJsonrpc() {
        return jsonrpc;
    }
    
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public Map<String, Object> getParams() {
        return params;
    }
    
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }
    
    public MCPError getError() {
        return error;
    }
    
    public void setError(MCPError error) {
        this.error = error;
    }
    
    // Utility methods
    public boolean isRequest() {
        return method != null && id != null;
    }
    
    public boolean isResponse() {
        return id != null && method == null && (result != null || error != null);
    }
    
    public boolean isNotification() {
        return method != null && id == null;
    }
    
    public boolean isError() {
        return error != null;
    }
}