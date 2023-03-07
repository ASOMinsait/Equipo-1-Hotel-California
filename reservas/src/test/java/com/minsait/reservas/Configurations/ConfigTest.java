package com.minsait.reservas.Configurations;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigTest {
    @InjectMocks
    Config config;
    @Test
    void testSpringOpenAPI() {
        config.springOpenAPI();
    }
    @Test
    void testRegistrarRestTemplate() {
        config.registrarRestTemplate();
    }
}