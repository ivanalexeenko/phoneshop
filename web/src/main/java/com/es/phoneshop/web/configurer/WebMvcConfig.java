package com.es.phoneshop.web.configurer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static com.es.phoneshop.web.helping.ConstantsWeb.*;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {WEB_MVC_CONFIG_BASE_PACKAGE})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(WEBJARS_PATH_PATTERN).addResourceLocations(WEBJARS_RESOURCE_LOCATION);
    }
}
