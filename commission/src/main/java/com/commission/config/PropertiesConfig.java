package com.commission.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("commission")
@Getter
@Setter
public class PropertiesConfig {
    private String version;
    private int standardPercent;
    private int bonus;
}
