package com.minsait.reservas.Configurations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {
    @InjectMocks
    SwaggerConfig swaggerConfig;
    @Test
    public void testSwagger() {
        String info= swaggerConfig.springOpenAPI().getInfo().getVersion();
        assertTrue(info.equals("0.0.1-SNAPSHOT"));
    }
}