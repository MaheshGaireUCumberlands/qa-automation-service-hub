package com.maheshgaire.qaautomation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class FreeAIConfig {

    @Value("${ai.service.provider:mock}")
    private String aiProvider;

    @Value("${ai.ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${ai.huggingface.base-url:https://api-inference.huggingface.co}")
    private String huggingFaceBaseUrl;

    @Bean
    public WebClient aiWebClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }

    @Bean
    public String aiServiceProvider() {
        return aiProvider;
    }

    @Bean
    public String ollamaBaseUrl() {
        return ollamaBaseUrl;
    }

    @Bean
    public String huggingFaceBaseUrl() {
        return huggingFaceBaseUrl;
    }
}