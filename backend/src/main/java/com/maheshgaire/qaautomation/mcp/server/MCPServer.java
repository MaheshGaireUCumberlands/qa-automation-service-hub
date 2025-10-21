package com.maheshgaire.qaautomation.mcp.server;

import com.maheshgaire.qaautomation.mcp.model.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * MCP Server interface defining the Model Context Protocol server capabilities
 */
public interface MCPServer {
    
    /**
     * Initialize the MCP server
     */
    Mono<Map<String, Object>> initialize(Map<String, Object> params);
    
    /**
     * List available tools
     */
    Mono<List<MCPTool>> listTools();
    
    /**
     * Call a specific tool
     */
    Mono<Object> callTool(String name, Map<String, Object> arguments);
    
    /**
     * List available resources
     */
    Mono<List<MCPResource>> listResources();
    
    /**
     * Read a specific resource
     */
    Mono<Object> readResource(String uri);
    
    /**
     * List available prompts
     */
    Mono<List<MCPPrompt>> listPrompts();
    
    /**
     * Get a specific prompt
     */
    Mono<Object> getPrompt(String name, Map<String, Object> arguments);
    
    /**
     * Handle notifications
     */
    Mono<Void> handleNotification(String method, Map<String, Object> params);
    
    /**
     * Get server capabilities
     */
    Map<String, Object> getCapabilities();
    
    /**
     * Get server information
     */
    Map<String, Object> getServerInfo();
}