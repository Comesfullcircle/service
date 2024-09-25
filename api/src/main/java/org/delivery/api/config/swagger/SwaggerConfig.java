package org.delivery.api.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class SwaggerConfig {

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper){
        return new ModelResolver(objectMapper);
    }
}