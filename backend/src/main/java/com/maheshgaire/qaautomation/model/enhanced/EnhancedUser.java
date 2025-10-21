package com.maheshgaire.qaautomation.model.enhanced;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Enhanced user model with relationships")
public class EnhancedUser {
    
    @Schema(description = "Unique user identifier")
    private String userId;
    
    @Schema(description = "User's first name")
    private String firstName;
    
    @Schema(description = "User's last name")
    private String lastName;
    
    @Schema(description = "User's email address")
    private String email;
    
    @Schema(description = "User's phone number")
    private String phoneNumber;
    
    @Schema(description = "User's date of birth")
    private String dateOfBirth;
    
    @Schema(description = "User's address")
    private Address address;
    
    @Schema(description = "User's profile information")
    private UserProfile profile;
    
    @Schema(description = "User's orders (if requested)")
    private List<Order> orders;
    
    @Schema(description = "User creation timestamp")
    private LocalDateTime createdAt;
    
    public EnhancedUser() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}