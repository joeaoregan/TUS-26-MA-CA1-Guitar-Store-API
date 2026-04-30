package edu.tus.guitarstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * addViewControllers method to map the root URL to the index.html page.
     * This ensures that when users access the base URL of the application,
     * they are served the static index.html file, which is typically the
     * entry point for a front-end application.
     */
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        // Explicitly map the root URL to the static index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
