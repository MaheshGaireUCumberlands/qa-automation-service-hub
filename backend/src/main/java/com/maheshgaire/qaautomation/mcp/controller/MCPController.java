package com.maheshgaire.qaautomation.mcp.controller;

import com.maheshgaire.qaautomation.mcp.model.MCPError;
import com.maheshgaire.qaautomation.mcp.model.MCPMessage;
import com.maheshgaire.qaautomation.mcp.server.MCPServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * MCP (Model Context Protocol) Controller
 * Provides MCP-compliant endpoints for AI tool integration
 */
@RestController
@RequestMapping("/api/v1/mcp")
@CrossOrigin(origins = "*")
@Tag(name = "MCP - Model Context Protocol", description = "MCP-compliant endpoints for AI tool integration")
public class MCPController {
    
    @Autowired
    private MCPServer mcpServer;
    
    @PostMapping(value = "/rpc", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "MCP JSON-RPC endpoint", 
               description = "Handle MCP protocol requests via JSON-RPC 2.0")
    public Mono<MCPMessage> handleRPC(@RequestBody MCPMessage request) {
        String requestId = request.getId();
        String method = request.getMethod();
        Map<String, Object> params = request.getParams() != null ? request.getParams() : new HashMap<>();
        
        try {
            return switch (method) {
                case "initialize" -> mcpServer.initialize(params)
                        .map(result -> MCPMessage.response(requestId, result))
                        .onErrorReturn(MCPMessage.error(requestId, MCPError.internalError("Initialization failed")));
                        
                case "tools/list" -> mcpServer.listTools()
                        .map(tools -> {
                            Map<String, Object> result = new HashMap<>();
                            result.put("tools", tools);
                            return MCPMessage.response(requestId, result);
                        })
                        .onErrorReturn(MCPMessage.error(requestId, MCPError.internalError("Failed to list tools")));
                        
                case "tools/call" -> {
                    String toolName = (String) params.get("name");
                    @SuppressWarnings("unchecked")
                    Map<String, Object> arguments = (Map<String, Object>) params.get("arguments");
                    
                    yield mcpServer.callTool(toolName, arguments != null ? arguments : new HashMap<>())
                            .map(result -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("content", result);
                                response.put("isError", false);
                                return MCPMessage.response(requestId, response);
                            })
                            .onErrorReturn(MCPMessage.error(requestId, MCPError.toolError("Tool execution failed")));
                }
                        
                case "resources/list" -> mcpServer.listResources()
                        .map(resources -> {
                            Map<String, Object> result = new HashMap<>();
                            result.put("resources", resources);
                            return MCPMessage.response(requestId, result);
                        })
                        .onErrorReturn(MCPMessage.error(requestId, MCPError.internalError("Failed to list resources")));
                        
                case "resources/read" -> {
                    String uri = (String) params.get("uri");
                    yield mcpServer.readResource(uri)
                            .map(content -> {
                                Map<String, Object> result = new HashMap<>();
                                result.put("contents", content);
                                return MCPMessage.response(requestId, result);
                            })
                            .onErrorReturn(MCPMessage.error(requestId, MCPError.resourceError("Failed to read resource")));
                }
                        
                case "prompts/list" -> mcpServer.listPrompts()
                        .map(prompts -> {
                            Map<String, Object> result = new HashMap<>();
                            result.put("prompts", prompts);
                            return MCPMessage.response(requestId, result);
                        })
                        .onErrorReturn(MCPMessage.error(requestId, MCPError.internalError("Failed to list prompts")));
                        
                case "prompts/get" -> {
                    String promptName = (String) params.get("name");
                    @SuppressWarnings("unchecked")
                    Map<String, Object> arguments = (Map<String, Object>) params.get("arguments");
                    
                    yield mcpServer.getPrompt(promptName, arguments != null ? arguments : new HashMap<>())
                            .map(prompt -> {
                                Map<String, Object> result = new HashMap<>();
                                result.put("description", "Generated prompt for " + promptName);
                                result.put("messages", prompt);
                                return MCPMessage.response(requestId, result);
                            })
                            .onErrorReturn(MCPMessage.error(requestId, MCPError.promptError("Failed to get prompt")));
                }
                        
                default -> Mono.just(MCPMessage.error(requestId, MCPError.methodNotFound(method)));
            };
        } catch (Exception e) {
            return Mono.just(MCPMessage.error(requestId, MCPError.internalError("Request processing failed: " + e.getMessage())));
        }
    }
    
    @GetMapping(value = "/capabilities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get MCP server capabilities", 
               description = "Get information about supported MCP features and tools")
    public Mono<Map<String, Object>> getCapabilities() {
        Map<String, Object> response = new HashMap<>();
        response.put("capabilities", mcpServer.getCapabilities());
        response.put("serverInfo", mcpServer.getServerInfo());
        response.put("protocolVersion", "2024-11-05");
        
        return Mono.just(response);
    }
    
    @GetMapping(value = "/tools", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List available MCP tools", 
               description = "Get all available tools that can be called via MCP")
    public Mono<Map<String, Object>> listTools() {
        return mcpServer.listTools()
                .map(tools -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("tools", tools);
                    response.put("count", tools.size());
                    return response;
                });
    }
    
    @PostMapping(value = "/tools/{toolName}/call", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Call a specific MCP tool", 
               description = "Execute a tool with provided arguments")
    public Mono<Map<String, Object>> callTool(
            @PathVariable String toolName,
            @RequestBody(required = false) Map<String, Object> arguments) {
        
        return mcpServer.callTool(toolName, arguments != null ? arguments : new HashMap<>())
                .map(result -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("tool", toolName);
                    response.put("result", result);
                    response.put("success", true);
                    return response;
                })
                .onErrorResume(error -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("tool", toolName);
                    response.put("error", error.getMessage());
                    response.put("success", false);
                    return Mono.just(response);
                });
    }
    
    @GetMapping(value = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List available MCP resources", 
               description = "Get all available resources that can be read via MCP")
    public Mono<Map<String, Object>> listResources() {
        return mcpServer.listResources()
                .map(resources -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("resources", resources);
                    response.put("count", resources.size());
                    return response;
                });
    }
    
    @GetMapping(value = "/resources/read", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Read a specific MCP resource", 
               description = "Get content of a resource by URI")
    public Mono<Map<String, Object>> readResource(@RequestParam String uri) {
        return mcpServer.readResource(uri)
                .map(content -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("uri", uri);
                    response.put("content", content);
                    response.put("success", true);
                    return response;
                })
                .onErrorResume(error -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("uri", uri);
                    response.put("error", error.getMessage());
                    response.put("success", false);
                    return Mono.just(response);
                });
    }
    
    @GetMapping(value = "/prompts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List available MCP prompts", 
               description = "Get all available prompts that can be generated via MCP")
    public Mono<Map<String, Object>> listPrompts() {
        return mcpServer.listPrompts()
                .map(prompts -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("prompts", prompts);
                    response.put("count", prompts.size());
                    return response;
                });
    }
    
    @PostMapping(value = "/prompts/{promptName}/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a specific MCP prompt", 
               description = "Generate a prompt with provided arguments")
    public Mono<Map<String, Object>> getPrompt(
            @PathVariable String promptName,
            @RequestBody(required = false) Map<String, Object> arguments) {
        
        return mcpServer.getPrompt(promptName, arguments != null ? arguments : new HashMap<>())
                .map(prompt -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("prompt", promptName);
                    response.put("content", prompt);
                    response.put("success", true);
                    return response;
                })
                .onErrorResume(error -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("prompt", promptName);
                    response.put("error", error.getMessage());
                    response.put("success", false);
                    return Mono.just(response);
                });
    }
    
    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "MCP integration demo", 
               description = "Demonstrate MCP capabilities with sample data")
    public Mono<Map<String, Object>> demo() {
        Map<String, Object> response = new HashMap<>();
        
        // Server info
        response.put("server", mcpServer.getServerInfo());
        response.put("capabilities", mcpServer.getCapabilities());
        
        // Sample tool call
        String demoId = UUID.randomUUID().toString();
        Map<String, Object> demoArgs = Map.of("type", "users", "count", 3);
        
        return mcpServer.callTool("generate_test_data", demoArgs)
                .map(result -> {
                    response.put("demo_tool_call", Map.of(
                        "tool", "generate_test_data",
                        "arguments", demoArgs,
                        "result", result
                    ));
                    response.put("demo_id", demoId);
                    response.put("mcp_ready", true);
                    return response;
                })
                .onErrorReturn(response);
    }
}