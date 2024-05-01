package com.zero.springweb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {

    private String sessionTokenSecretKey;
    private String refreshTokenSecretKey;
    private Duration tokenDuration;
}
