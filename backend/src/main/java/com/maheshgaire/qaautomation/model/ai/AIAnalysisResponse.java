package com.maheshgaire.qaautomation.model.ai;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "AI analysis response model")
public class AIAnalysisResponse {
    
    @Schema(description = "Analysis ID for tracking")
    private String analysisId;
    
    @Schema(description = "Type of analysis performed")
    private String analysisType;
    
    @Schema(description = "AI-generated summary")
    private String summary;
    
    @Schema(description = "Detailed analysis insights")
    private List<AnalysisInsight> insights;
    
    @Schema(description = "AI recommendations")
    private List<Recommendation> recommendations;
    
    @Schema(description = "Key metrics extracted from data")
    private Map<String, Object> metrics;
    
    @Schema(description = "Analysis confidence score (0-1)")
    private Double confidenceScore;
    
    @Schema(description = "Processing time in milliseconds")
    private Long processingTimeMs;
    
    @Schema(description = "Analysis completion timestamp")
    private LocalDateTime completedAt;
    
    @Schema(description = "Model used for analysis")
    private String modelUsed;

    public AIAnalysisResponse() {
        this.completedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<AnalysisInsight> getInsights() {
        return insights;
    }

    public void setInsights(List<AnalysisInsight> insights) {
        this.insights = insights;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getModelUsed() {
        return modelUsed;
    }

    public void setModelUsed(String modelUsed) {
        this.modelUsed = modelUsed;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Individual analysis insight")
    public static class AnalysisInsight {
        
        @Schema(description = "Insight category")
        private String category;
        
        @Schema(description = "Insight description")
        private String description;
        
        @Schema(description = "Importance level (1-5)")
        private Integer importance;
        
        @Schema(description = "Supporting data or evidence")
        private Object evidence;

        // Getters and Setters
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getImportance() {
            return importance;
        }

        public void setImportance(Integer importance) {
            this.importance = importance;
        }

        public Object getEvidence() {
            return evidence;
        }

        public void setEvidence(Object evidence) {
            this.evidence = evidence;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "AI-generated recommendation")
    public static class Recommendation {
        
        @Schema(description = "Recommendation type")
        private String type;
        
        @Schema(description = "Recommendation description")
        private String description;
        
        @Schema(description = "Priority level (low, medium, high, critical)")
        private String priority;
        
        @Schema(description = "Implementation effort estimate")
        private String effort;
        
        @Schema(description = "Expected impact")
        private String impact;

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getEffort() {
            return effort;
        }

        public void setEffort(String effort) {
            this.effort = effort;
        }

        public String getImpact() {
            return impact;
        }

        public void setImpact(String impact) {
            this.impact = impact;
        }
    }
}