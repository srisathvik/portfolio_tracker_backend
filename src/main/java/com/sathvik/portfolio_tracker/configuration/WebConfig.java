package com.sathvik.portfolio_tracker.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig  implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*");
    }
}
