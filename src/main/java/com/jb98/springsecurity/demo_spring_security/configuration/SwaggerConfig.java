package com.jb98.springsecurity.demo_spring_security.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "Demo Spring Security API",
                version = "1.0",
                description = "This API is a demo for a Spring Security application using JWT for authentication and authorization.",
                contact = @Contact(
                        name = "Bryan Corrales",
                        url = "https://github.com/JBryan98",
                        email = "bryan.corrales230198@gmail.com"
                )
        ),
        security = @SecurityRequirement(name = "Security token")
)
@SecurityScheme(
        name = "Security token",
        description = "Access token for secured endpoints",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
