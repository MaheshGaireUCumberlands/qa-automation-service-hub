package com.maheshgaire.qaautomation.model.enhanced;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "User profile information")
public class UserProfile {
    
    @Schema(description = "User's company")
    private String company;
    
    @Schema(description = "User's job title")
    private String jobTitle;
    
    @Schema(description = "User's bio or description")
    private String bio;
    
    @Schema(description = "User's website URL")
    private String website;
    
    @Schema(description = "User's preferred language")
    private String language;
    
    @Schema(description = "User's timezone")
    private String timezone;
    
    @Schema(description = "User's avatar URL")
    private String avatarUrl;

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}