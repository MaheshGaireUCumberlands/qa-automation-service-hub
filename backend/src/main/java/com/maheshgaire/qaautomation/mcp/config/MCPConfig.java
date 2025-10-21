package com.maheshgaire.qaautomation.mcp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * MCP (Model Context Protocol) Configuration
 */
@Configuration
public class MCPConfig {
    
    @Value("${mcp.enabled:true}")
    private boolean mcpEnabled;
    
    @Value("${mcp.server.name:qa-automation-hub}")
    private String serverName;
    
    @Value("${mcp.server.version:1.0.0}")
    private String serverVersion;
    
    @Value("${mcp.tools.enabled:true}")
    private boolean toolsEnabled;
    
    @Value("${mcp.resources.enabled:true}")
    private boolean resourcesEnabled;
    
    @Value("${mcp.prompts.enabled:true}")
    private boolean promptsEnabled;
    
    @Bean
    public WebClient mcpWebClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) // 2MB
                .build();
    }
    
    @Bean
    public boolean isMcpEnabled() {
        return mcpEnabled;
    }
    
    @Bean
    public boolean areToolsEnabled() {
        return toolsEnabled;
    }
    
    @Bean
    public boolean areResourcesEnabled() {
        return resourcesEnabled;
    }
    
    @Bean
    public boolean arePromptsEnabled() {
        return promptsEnabled;
    }
    
    public String getServerName() {
        return serverName;
    }
    
    public String getServerVersion() {
        return serverVersion;
    }
}