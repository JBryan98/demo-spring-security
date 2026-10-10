package com.jb98.springsecurity.demo_spring_security.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "application.security")
public record SecurityConfigPropertiesMapper(
        JWT jwt,
        Cors cors
) {

    protected record JWT(
            String secretKey,
            long expiration
    ) {
    }

    protected record Cors(
            List<String> allowedOrigins,
            List<String> allowedMethods,
            List<String> allowedHeaders
    ) {
    }

}
