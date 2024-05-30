package com.jpmc.netbanking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(
info = @Info(
        title = "Netbanking api",
        description = "Application for internet banking",
        version = "v1"
)
)
@Configuration
public class OpenApiConfig {

}
