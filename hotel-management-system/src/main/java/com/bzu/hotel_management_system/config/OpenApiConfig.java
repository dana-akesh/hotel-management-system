package com.bzu.hotel_management_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Burble Productions",
                        email = "burble_productions@gmail.com",
                        url = "https://burbleproductions.com"
                ),
                description = "API Description\n\n" +
                        "### Contact Information\n\n" +
                        "1. Dana Akesh\n" +
                        "   - Email: [danaakesh@gmail.com](mailto:danaakesh@gmail.com)\n" +
                        "2. Baker Al-Sdeeq Dwaikat\n" +
                        "   - Email: [bakerdwaikat02@gmail.com](mailto:bakerdwaikat02@gmail.com)\n",
                title = "OpenApi specification for Hotel Management System",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ), servers = {
        @Server(
                description = "Local ENV",
                url = "http://localhost:8080"
        ),
        @Server(
                description = "PROD ENV",
                url = "https://some-url.com"
        )
},
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
