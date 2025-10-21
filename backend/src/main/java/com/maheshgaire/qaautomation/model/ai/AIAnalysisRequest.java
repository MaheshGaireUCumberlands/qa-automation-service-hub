package com.maheshgaire.qaautomation.model.ai;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "AI analysis request model")
public class AIAnalysisRequest {
    
    @Schema(description = "Type of analysis requested")
    private String analysisType;
    
    @Schema(description = "Test data to analyze")
    private Object testData;
    
    @Schema(description = "Analysis context and parameters")
    private Map<String, Object> context;
    
    @Schema(description = "Custom prompt or instructions")
    private String customPrompt;
    
    @Schema(description = "Analysis options")
    private AnalysisOptions options;
    
    @Schema(description = "Request timestamp")
    private LocalDateTime requestTime;

    public AIAnalysisRequest() {
        this.requestTime = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public Object getTestData() {
        return testData;
    }

    public void setTestData(Object testData) {
        this.testData = testData;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getCustomPrompt() {
        return customPrompt;
    }

    public void setCustomPrompt(String customPrompt) {
        this.customPrompt = customPrompt;
    }

    public AnalysisOptions getOptions() {
        return options;
    }

    public void setOptions(AnalysisOptions options) {
        this.options = options;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Analysis configuration options")
    public static class AnalysisOptions {
        
        @Schema(description = "Include detailed explanations")
        private Boolean includeDetails;
        
        @Schema(description = "Generate test recommendations")
        private Boolean includeRecommendations;
        
        @Schema(description = "Analysis focus areas")
        private List<String> focusAreas;
        
        @Schema(description = "Output format preference")
        private String outputFormat;

        // Getters and Setters
        public Boolean getIncludeDetails() {
            return includeDetails;
        }

        public void setIncludeDetails(Boolean includeDetails) {
            this.includeDetails = includeDetails;
        }

        public Boolean getIncludeRecommendations() {
            return includeRecommendations;
        }

        public void setIncludeRecommendations(Boolean includeRecommendations) {
            this.includeRecommendations = includeRecommendations;
        }

        public List<String> getFocusAreas() {
            return focusAreas;
        }

        public void setFocusAreas(List<String> focusAreas) {
            this.focusAreas = focusAreas;
        }

        public String getOutputFormat() {
            return outputFormat;
        }

        public void setOutputFormat(String outputFormat) {
            this.outputFormat = outputFormat;
        }
    }
}