package com.maheshgaire.qaautomation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web Controller for serving frontend pages
 */
@Controller
public class WebController {

    /**
     * Serve the main dashboard page at root
     */
    @GetMapping("/")
    public String index() {
        return "forward:/test-dashboard.html";
    }

    /**
     * Serve the test dashboard directly
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/test-dashboard.html";
    }
    
    /**
     * Serve the test dashboard with .html extension
     */
    @GetMapping("/test-dashboard")
    public String testDashboard() {
        return "forward:/test-dashboard.html";
    }
}