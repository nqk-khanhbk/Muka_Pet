package com.muka.petcare.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private String accessTokenSecret;
    private String refreshTokenSecret;
    private Long accessTokenValidity;
    private Long refreshTokenValidity;
}
