package com.plazoleta.msrestaurant.infrastructure.security.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private static final Logger log = LoggerFactory.getLogger(JwtProperties.class);

    private String secret;
    private Long expiration;

    @PostConstruct
    public void logJwtConfig() {
        log.info("🔐 JWT configuration loaded: secret={}, expiration={}", secret, expiration);
    }
}
