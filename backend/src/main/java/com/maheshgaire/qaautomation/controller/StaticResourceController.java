package com.maheshgaire.qaautomation.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
public class StaticResourceController {

    @GetMapping("/")
    public Mono<ServerResponse> home() {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(getTestDashboardContent());
    }

    @GetMapping("/test-dashboard.html")
    public Mono<ServerResponse> testDashboard() {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(getTestDashboardContent());
    }

    private String getTestDashboardContent() {
        try {
            Resource resource = new ClassPathResource("static/test-dashboard.html");
            if (resource.exists()) {
                return new String(resource.getInputStream().readAllBytes());
            } else {
                return "<!DOCTYPE html>" +
                       "<html>" +
                       "<head>" +
                       "    <title>QA Automation Service Hub</title>" +
                       "    <script src=\"https://cdn.tailwindcss.com\"></script>" +
                       "</head>" +
                       "<body class=\"bg-gray-100\">" +
                       "    <div class=\"container mx-auto p-6\">" +
                       "        <div class=\"bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded\">" +
                       "            <h1 class=\"text-xl font-bold\">Static File Not Found</h1>" +
                       "            <p>The test-dashboard.html file was not found in the static resources.</p>" +
                       "            <p>This indicates an issue with the build process.</p>" +
                       "        </div>" +
                       "        <div class=\"mt-4 bg-blue-100 border border-blue-400 text-blue-700 px-4 py-3 rounded\">" +
                       "            <h2 class=\"font-bold\">Available Endpoints:</h2>" +
                       "            <ul class=\"list-disc list-inside mt-2\">" +
                       "                <li><a href=\"/actuator/health\" class=\"underline\">Health Check</a></li>" +
                       "                <li><a href=\"/api/v1/testdata/templates\" class=\"underline\">API Templates</a></li>" +
                       "                <li><a href=\"/actuator/info\" class=\"underline\">Application Info</a></li>" +
                       "            </ul>" +
                       "        </div>" +
                       "    </div>" +
                       "</body>" +
                       "</html>";
            }
        } catch (Exception e) {
            return "<!DOCTYPE html>" +
                   "<html>" +
                   "<head>" +
                   "    <title>QA Automation Service Hub - Error</title>" +
                   "    <script src=\"https://cdn.tailwindcss.com\"></script>" +
                   "</head>" +
                   "<body class=\"bg-gray-100\">" +
                   "    <div class=\"container mx-auto p-6\">" +
                   "        <div class=\"bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded\">" +
                   "            <h1 class=\"text-xl font-bold\">Error Loading Dashboard</h1>" +
                   "            <p>Error: " + e.getMessage() + "</p>" +
                   "            <p>Please check the application logs for more details.</p>" +
                   "        </div>" +
                   "    </div>" +
                   "</body>" +
                   "</html>";
        }
    }
}