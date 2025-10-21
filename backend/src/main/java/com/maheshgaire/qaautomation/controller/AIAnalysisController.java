package com.maheshgaire.qaautomation.controller;

import com.maheshgaire.qaautomation.model.ai.AIAnalysisRequest;
import com.maheshgaire.qaautomation.model.ai.AIAnalysisResponse;
import com.maheshgaire.qaautomation.service.AIAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@CrossOrigin(origins = "*")
@Tag(name = "AI Analysis", description = "FREE AI-powered test data analysis and recommendations")
public class AIAnalysisController {

    @Autowired
    private AIAnalysisService aiAnalysisService;

    @Value("${ai.service.provider:mock}")
    private String aiProvider;

    @PostMapping(value = "/analyze", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Analyze test data with AI", 
               description = "Get AI-powered insights and recommendations for your test data (FREE)")
    public Mono<AIAnalysisResponse> analyzeTestData(@RequestBody AIAnalysisRequest request) {
        AIAnalysisResponse response = aiAnalysisService.analyzeTestData(request);
        return Mono.just(response);
    }

    @PostMapping(value = "/summarize", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Summarize test results", 
               description = "Generate AI-powered summary of test results with insights")
    public Mono<AIAnalysisResponse> summarizeTestResults(
            @RequestBody Object testResults,
            @Parameter(description = "Focus areas for analysis")
            @RequestParam(required = false) List<String> focusAreas) {
        
        if (focusAreas == null) {
            focusAreas = Arrays.asList("data_quality", "coverage", "performance");
        }
        
        AIAnalysisResponse response = aiAnalysisService.summarizeTestResults(testResults, focusAreas);
        return Mono.just(response);
    }

    @PostMapping(value = "/document", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate test documentation", 
               description = "Create AI-powered test case documentation")
    public Mono<AIAnalysisResponse> generateTestDocumentation(
            @RequestBody Object testData,
            @Parameter(description = "Focus area for documentation")
            @RequestParam(defaultValue = "test_cases") String focusArea) {
        
        AIAnalysisResponse response = aiAnalysisService.generateTestDocumentation(testData, focusArea);
        return Mono.just(response);
    }

    @GetMapping(value = "/capabilities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get AI capabilities", 
               description = "Get information about available AI analysis features")
    public Mono<Map<String, Object>> getAICapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        
        capabilities.put("current_provider", aiProvider);
        capabilities.put("cost", "FREE");
        
        Map<String, Object> providers = new HashMap<>();
        providers.put("mock", Map.of(
            "description", "Intelligent mock analyzer - Always available",
            "cost", "FREE",
            "features", Arrays.asList("Smart analysis", "Pattern recognition", "Recommendations")
        ));
        providers.put("ollama", Map.of(
            "description", "Local AI models - Completely free",
            "cost", "FREE",
            "setup", "Install Ollama locally: https://ollama.ai",
            "features", Arrays.asList("Local processing", "No API costs", "Privacy-focused")
        ));
        providers.put("huggingface", Map.of(
            "description", "Hugging Face API - Free tier available",
            "cost", "FREE (with limits)",
            "setup", "Get free API key: https://huggingface.co",
            "features", Arrays.asList("Advanced models", "Fast responses", "Good free tier")
        ));
        
        capabilities.put("available_providers", providers);
        
        Map<String, Object> analysisTypes = new HashMap<>();
        analysisTypes.put("summary", "Comprehensive test data summary with insights");
        analysisTypes.put("documentation", "Automated test case documentation generation");
        analysisTypes.put("recommendations", "Actionable improvement recommendations");
        analysisTypes.put("patterns", "Pattern and anomaly detection in test data");
        
        capabilities.put("analysis_types", analysisTypes);
        
        capabilities.put("setup_instructions", Map.of(
            "current_mode", aiProvider,
            "to_use_ollama", "1. Install Ollama, 2. Set ai.service.provider=ollama",
            "to_use_huggingface", "1. Get HF API key, 2. Set ai.service.provider=huggingface"
        ));
        
        return Mono.just(capabilities);
    }

    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "AI analysis demo", 
               description = "Demonstrate AI analysis capabilities with sample data")
    public Mono<AIAnalysisResponse> demoAIAnalysis(
            @Parameter(description = "Type of demo analysis")
            @RequestParam(defaultValue = "summary") String analysisType) {
        
        // Create sample test data for demo
        Map<String, Object> sampleData = Map.of(
            "test_suite", "User Registration Tests",
            "total_tests", 25,
            "passed", 22,
            "failed", 2,
            "skipped", 1,
            "coverage", 0.88,
            "execution_time_ms", 15420,
            "test_categories", Arrays.asList("positive", "negative", "edge_cases", "integration")
        );
        
        AIAnalysisRequest request = new AIAnalysisRequest();
        request.setAnalysisType(analysisType);
        request.setTestData(sampleData);
        request.setCustomPrompt("This is a demo analysis of a sample test suite. Provide insights and recommendations.");
        
        AIAnalysisResponse response = aiAnalysisService.analyzeTestData(request);
        return Mono.just(response);
    }
}